package com.cleanChoice.cleanChoice.domain.home.service;

import com.cleanChoice.cleanChoice.domain.embedding.dto.EmbResponseDto;
import com.cleanChoice.cleanChoice.domain.embedding.service.FlaskApiService;
import com.cleanChoice.cleanChoice.domain.home.dto.request.AnalyzeRequestDto;
import com.cleanChoice.cleanChoice.domain.home.dto.response.AnalyzeResponseDto;
import com.cleanChoice.cleanChoice.domain.openAi.service.OpenAiService;
import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.product.domain.*;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.NameBrandNameVectorRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductMarketRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.dto.NameVectorWithDistanceDto;
import com.cleanChoice.cleanChoice.domain.product.dto.response.AnalyzeType;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductMarketResponseDto;
import com.cleanChoice.cleanChoice.domain.viewRecord.domain.repository.ViewRecordRepository;
import com.cleanChoice.cleanChoice.domain.viewRecord.service.ViewRecordService;
import com.cleanChoice.cleanChoice.global.dtoMapper.DtoMapperUtil;
import com.cleanChoice.cleanChoice.global.exceptions.BadRequestException;
import com.cleanChoice.cleanChoice.global.exceptions.ErrorCode;
import com.cleanChoice.cleanChoice.global.util.URLValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final NameBrandNameVectorRepository nameBrandNameVectorRepository;
    private final ProductMarketRepository productMarketRepository;

    private final DtoMapperUtil dtoMapperUtil;

    private final FlaskApiService flaskApiService;
    private final OpenAiService openAiService;
    private final ViewRecordRepository viewRecordRepository;
    private final ViewRecordService viewRecordService;

    @Transactional
    public AnalyzeResponseDto analyze(
            Member member,
            AnalyzeRequestDto analyzeRequestDto
    ) {
        Mono<Boolean> urlValidation = URLValidator.validateUrl(analyzeRequestDto.getUrl());
        Mono<Boolean> imageUrlValidation = URLValidator.validateImageUrl(analyzeRequestDto.getImageUrl());

        List<ProductMarket> productMarketList = productMarketRepository.findAllByUrl(analyzeRequestDto.getUrl());

        if (urlValidation.blockOptional().orElse(false)) {
            if (!productMarketList.isEmpty()) {
                productMarketRepository.delete(productMarketList.get(0));
            }
            throw new BadRequestException(ErrorCode.INVALID_PARAMETER, "Invalid URL");
        }

        if (imageUrlValidation.blockOptional().orElse(false)) {
            throw new BadRequestException(ErrorCode.INVALID_PARAMETER, "Invalid Image URL");
        }

        if (!productMarketList.isEmpty()) {
            ProductMarket productMarket = productMarketList.get(0);

            return dtoMapperUtil.toAnalyzeResponseDto(productMarket, AnalyzeType.DB_ANALYZE);
        }

        NameVectorWithDistanceDto nameVectorWithDistanceDto = null;

        /*
        // 둘 중 하나라도 null이면 바로 llm으로 토스
        if (analyzeRequestDto.getProductName() == null || analyzeRequestDto.getBrandName() == null) {
        }
         */

        // analyzeRequestDto에서 productName, brandName을 가져와서 Flask API에 보내서 embedding vector를 받아옴
        EmbResponseDto embResponseDto = flaskApiService.getEmbeddingVector(
                analyzeRequestDto.getProductName(),
                analyzeRequestDto.getBrandName()
        );

        // productName, brandName에 대한 embedding vector를 DB에서 가져옴
        nameVectorWithDistanceDto = findNameBrandNameVectorByCosineDistanceTop1WithDistance(embResponseDto)
                .orElse(null);

        // Cosine Distance search 결과 없거나, ProductMarket에 대한 결과가 없는 경우
        if (nameVectorWithDistanceDto == null) {
            // LLM 호출
            ProductMarket productMarket = this.getProductMarketByLLMRequest(
                    analyzeRequestDto.getUrl(),
                    null
            );

            // view record 저장
            viewRecordService.createViewRecord(member, productMarket);

            EmbResponseDto newEmbResponseDto = flaskApiService.getEmbeddingVector(
                    productMarket.getProduct().getName(),
                    productMarket.getProduct().getBrandName()
            );

            NameBrandNameVector nameBrandNameVector = NameBrandNameVector.createWithProductMarket(
                    productMarket,
                    newEmbResponseDto.getProductNameVectorList(),
                    newEmbResponseDto.getBrandNameVectorList()
            );
            nameBrandNameVectorRepository.save(nameBrandNameVector);

            return dtoMapperUtil.toAnalyzeResponseDto(productMarket, AnalyzeType.LLM_ANALYZE);
        }
        // ProductMarket에 대한 결과 없는 경우
        else if (nameVectorWithDistanceDto.getNameBrandNameVector().getProductMarket() == null) {
            ProductMarket productMarket = ProductMarket.of(
                    nameVectorWithDistanceDto.getNameBrandNameVector().getProduct(),
                    analyzeRequestDto.getImageUrl(),
                    analyzeRequestDto.getUrl(),
                    analyzeRequestDto.getPrice(),
                    analyzeRequestDto.getPriceUnit()
            );

            productMarket = productMarketRepository.save(productMarket);

            NameBrandNameVector nameBrandNameVector = NameBrandNameVector.createWithProductMarket(
                    productMarket,
                    embResponseDto.getProductNameVectorList(),
                    embResponseDto.getBrandNameVectorList()
            );

            nameBrandNameVectorRepository.save(nameBrandNameVector);

            return dtoMapperUtil.toAnalyzeResponseDto(
                    productMarket,
                    AnalyzeType.DB_MAKE
            );
        }
        // ProductMarket에 대한 결과 있는 경우
        else {
            ProductMarket productMarket = nameVectorWithDistanceDto.getNameBrandNameVector().getProductMarket();

            return dtoMapperUtil.toAnalyzeResponseDto(productMarket, AnalyzeType.DB_ANALYZE);
        }
    }

    @Transactional
    public ProductMarketResponseDto resultCorrect(Member member, Long productMarketId, Boolean isCorrect, AnalyzeType analyzeType) {
        if (analyzeType.equals(AnalyzeType.LLM_ANALYZE))
            throw new BadRequestException(ErrorCode.INVALID_PARAMETER, "AnalyzeType LLM_ANALYZE is now allowed in this API");

        ProductMarket productMarket = productMarketRepository.findById(productMarketId).orElseThrow(
                () -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST, "Product Market does not exist in DB")
        );

        // 맞았을 시
        if (isCorrect) {
            // view record 저장
            viewRecordService.createViewRecord(member, productMarket);

            return dtoMapperUtil.toProductMarketResponseDto(productMarket);
        }
        // 틀렸을 시 LLM 호출
        else {
            if (analyzeType.equals(AnalyzeType.DB_MAKE)) {
                ProductMarket wrongProductMarket = productMarketRepository.findById(productMarketId).orElseThrow(
                        () -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST, "Product Market does not exist in DB")
                );
                NameBrandNameVector wrongNameBrandNameVector = nameBrandNameVectorRepository.findByProductMarket(wrongProductMarket).orElseThrow(
                        () -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST, "NameBrandNameVector does not exist in DB")
                );

                productMarketRepository.delete(wrongProductMarket);
                nameBrandNameVectorRepository.delete(wrongNameBrandNameVector);
            }

            productMarket = getProductMarketByLLMRequest(productMarket.getUrl(), productMarket.getProduct());

            // view record 저장
            viewRecordService.createViewRecord(member, productMarket);

            EmbResponseDto embResponseDto = flaskApiService.getEmbeddingVector(
                    productMarket.getProduct().getName(),
                    productMarket.getProduct().getBrandName()
            );

            NameBrandNameVector nameBrandNameVector = NameBrandNameVector.createWithProductMarket(
                    productMarket,
                    embResponseDto.getProductNameVectorList(),
                    embResponseDto.getBrandNameVectorList()
            );
            nameBrandNameVectorRepository.save(nameBrandNameVector);

            return dtoMapperUtil.toProductMarketResponseDto(productMarket);
        }
    }

    private Optional<NameVectorWithDistanceDto> findNameBrandNameVectorByCosineDistanceTop1WithDistance(EmbResponseDto embResponseDto) {
        List<Object []> resultList = nameBrandNameVectorRepository.findNameBrandNameVectorByCosineDistanceTop1WithDistance(
                embResponseDto.getProductNameVectorList().toString(),
                embResponseDto.getBrandNameVectorList().toString()
        );

        if (resultList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(
                NameVectorWithDistanceDto.of(
                        (NameBrandNameVector) resultList.get(0)[0],
                        (Double) resultList.get(0)[1],
                        (Double) resultList.get(0)[2]
                )
        );
    }

    private ProductMarket getProductMarketByLLMRequest(String url, Product product) {
        ProductMarket productMarket = openAiService.packProductMarket(url, product);

        // cascade로 인해 product, productIngredientJoin, productLabelStatement, productMarket, productIngredientJoin, ingredient 모두 저장됨
        return productMarketRepository.save(productMarket);
    }

}
