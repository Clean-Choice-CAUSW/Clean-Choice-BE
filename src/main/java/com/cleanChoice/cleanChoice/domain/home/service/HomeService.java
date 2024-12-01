package com.cleanChoice.cleanChoice.domain.home.service;

import com.cleanChoice.cleanChoice.domain.embTest.dto.EmbResponseDto;
import com.cleanChoice.cleanChoice.domain.embTest.service.FlaskApiService;
import com.cleanChoice.cleanChoice.domain.home.dto.request.AnalyzeRequestDto;
import com.cleanChoice.cleanChoice.domain.home.dto.response.AnalyzeResponseDto;
import com.cleanChoice.cleanChoice.domain.home.openAi.service.LLMService;
import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.product.domain.*;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.NameBrandNameVectorRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductMarketRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.dto.NameVectorWithDistanceDto;
import com.cleanChoice.cleanChoice.domain.product.dto.response.AnalyzeType;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductMarketResponseDto;
import com.cleanChoice.cleanChoice.domain.viewRecord.domain.ViewRecord;
import com.cleanChoice.cleanChoice.domain.viewRecord.domain.repository.ViewRecordRepository;
import com.cleanChoice.cleanChoice.global.dtoMapper.DtoMapperUtil;
import com.cleanChoice.cleanChoice.global.exceptions.BadRequestException;
import com.cleanChoice.cleanChoice.global.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final LLMService llmService;
    private final ViewRecordRepository viewRecordRepository;

    @Transactional
    public AnalyzeResponseDto analyze(
            Member member,
            AnalyzeRequestDto analyzeRequestDto
    ) {
        List<ProductMarket> productMarketList = productMarketRepository.findAllByUrl(analyzeRequestDto.getUrl());
        if (!productMarketList.isEmpty()) {
            ProductMarket productMarket = productMarketList.get(0);

            return dtoMapperUtil.toAnalyzeResponseDto(productMarket, AnalyzeType.DB_ANALYZE);
        }

        // analyzeRequestDto에서 productName, brandName을 가져와서 Flask API에 보내서 embedding vector를 받아옴
        EmbResponseDto embResponseDto = flaskApiService.getEmbeddingVector(
                analyzeRequestDto.getProductName(),
                analyzeRequestDto.getBrandName()
        );

        // productName, brandName에 대한 embedding vector를 DB에서 가져옴
        NameVectorWithDistanceDto nameVectorWithDistanceDto = findNameBrandNameVectorByCosineDistanceTop1WithDistance(embResponseDto)
                .orElse(null);

        // Cosine Distance search 결과 없거나, ProductMarket에 대한 결과가 없는 경우
        if (nameVectorWithDistanceDto == null ||
                nameVectorWithDistanceDto.getNameBrandNameVector().getProductMarket() == null
        ) {
            // LLM 호출
            ProductMarket productMarket = this.getProductMarketByLLMRequest(
                    analyzeRequestDto.getUrl(),
                    nameVectorWithDistanceDto == null ?
                            null :
                            nameVectorWithDistanceDto.getNameBrandNameVector().getProduct()
            );

            // view record 저장
            ViewRecord viewRecord = ViewRecord.of(member, productMarket);
            viewRecordRepository.save(viewRecord);

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
        // ProductMarket에 대한 결과 있는 경우
        else {
            ProductMarket productMarket = nameVectorWithDistanceDto.getNameBrandNameVector().getProductMarket();

            return dtoMapperUtil.toAnalyzeResponseDto(productMarket, AnalyzeType.DB_ANALYZE);
        }
    }

    @Transactional
    public ProductMarketResponseDto resultCorrect(Member member, Long productMarketId, Boolean isCorrect) {
        ProductMarket productMarket = productMarketRepository.findById(productMarketId).orElseThrow(
                () -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST, "Product Market does not exist in DB")
        );

        // 맞았을 시
        if (isCorrect) {
            productMarket.updateViewCount();
            productMarketRepository.save(productMarket);

            // view record 저장
            ViewRecord viewRecord = ViewRecord.of(member, productMarket);
            viewRecordRepository.save(viewRecord);

            return dtoMapperUtil.toProductMarketResponseDto(productMarket);
        }
        // 틀렸을 시 LLM 호출
        else {
            productMarket = getProductMarketByLLMRequest(productMarket.getUrl(), productMarket.getProduct());

            // view record 저장
            ViewRecord viewRecord = ViewRecord.of(member, productMarket);
            viewRecordRepository.save(viewRecord);

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
        ProductMarket productMarket = llmService.packProductMarket(url, product);

        // cascade로 인해 product, productIngredientJoin, productLabelStatement, productMarket, productIngredientJoin, ingredient 모두 저장됨
        return productMarketRepository.save(productMarket);
    }

}
