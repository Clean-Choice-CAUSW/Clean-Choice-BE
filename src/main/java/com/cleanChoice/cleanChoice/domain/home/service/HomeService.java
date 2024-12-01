package com.cleanChoice.cleanChoice.domain.home.service;

import com.cleanChoice.cleanChoice.domain.embTest.dto.EmbRequestDto;
import com.cleanChoice.cleanChoice.domain.embTest.dto.EmbResponseDto;
import com.cleanChoice.cleanChoice.domain.embTest.service.FlaskApiService;
import com.cleanChoice.cleanChoice.domain.home.openAi.dto.convert.ProductIngredientJoinLLMResponseDto;
import com.cleanChoice.cleanChoice.domain.home.openAi.dto.convert.ProductLabelStatementLLMResponseDto;
import com.cleanChoice.cleanChoice.domain.home.dto.request.AnalyzeRequestDto;
import com.cleanChoice.cleanChoice.domain.home.dto.response.AnalyzeResponseDto;
import com.cleanChoice.cleanChoice.domain.home.openAi.dto.convert.ProductMarketLLMResponseDto;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.BanType;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.BanedIngredientInfo;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.Ingredient;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.repository.IngredientRepository;
import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.product.domain.*;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.NameBrandNameVectorRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductIngredientJoinRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductMarketRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.dto.NameVectorWithDistanceDto;
import com.cleanChoice.cleanChoice.domain.product.dto.response.AnalyzeType;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductMarketResponseDto;
import com.cleanChoice.cleanChoice.global.dtoMapper.DtoMapperUtil;
import com.cleanChoice.cleanChoice.global.exceptions.BadRequestException;
import com.cleanChoice.cleanChoice.global.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final ProductRepository productRepository;
    private final NameBrandNameVectorRepository nameBrandNameVectorRepository;

    private final DtoMapperUtil dtoMapperUtil;

    private final FlaskApiService flaskApiService;
    private final ProductMarketRepository productMarketRepository;
    private final IngredientRepository ingredientRepository;
    private final ProductIngredientJoinRepository productIngredientJoinRepository;

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
        List<EmbRequestDto> embRequestDtoList = List.of(
                EmbRequestDto.builder()
                        .productName(analyzeRequestDto.getProductName())
                        .brandName(analyzeRequestDto.getBrandName())
                        .build()
        );
        EmbResponseDto embResponseDto = flaskApiService.getEmbeddingVectorList(embRequestDtoList).get(0);

        if (embResponseDto == null) {
            throw new InternalException("Empty response from Flask API");
        }

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
            return dtoMapperUtil.toProductMarketResponseDto(productMarket);
        }
        // 틀렸을 시 LLM 호출
        else {
            return dtoMapperUtil.toProductMarketResponseDto(
                    getProductMarketByLLMRequest(productMarket.getUrl(), productMarket.getProduct())
            );
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

    private ProductMarketLLMResponseDto llmRequest(String url) {
        // TODO: LLM에 url 보내서 ProductMarketLLMResponseDto로 받아오기
        return null;
    }

    @Transactional
    public ProductMarket getProductMarketByLLMRequest(String url, Product product) {
        ProductMarketLLMResponseDto productMarketLLMResponseDto = this.llmRequest(url);

        if (product == null) {
            // 기존 Product 없으면 그냥 새로 Product 만들기(단, dsldId랑 dsldUrl은 null로)
            // 여기선 ProductIngredientJoin, ProductLabelStatement 빈 배열
            product = Product.of(
                    null,
                    null,
                    productMarketLLMResponseDto.getName(),
                    productMarketLLMResponseDto.getBrandName(),
                    productMarketLLMResponseDto.getMadeInCountry(),
                    productMarketLLMResponseDto.getEnglishNetContent(),
                    productMarketLLMResponseDto.getKoreanNetContent(),
                    productMarketLLMResponseDto.getServingSize(),
                    productMarketLLMResponseDto.getEnglishProductType(),
                    productMarketLLMResponseDto.getKoreanProductType(),
                    productMarketLLMResponseDto.getEnglishSupplementForm(),
                    productMarketLLMResponseDto.getKoreanSupplementForm(),
                    productMarketLLMResponseDto.getEnglishSuggestedUse(),
                    productMarketLLMResponseDto.getKoreanSuggestedUse(),
                    productMarketLLMResponseDto.getEnglishOtherIngredients(),
                    productMarketLLMResponseDto.getKoreanOtherIngredients()
            );
        } else {
            // 기존에 Product 있을 시 해당 Product를 받아온 ProductMarketLLM 해석해서 정보 업데이트
            // 여기선 ProductIngredientJoin, ProductLabelStatement 빈 배열
            product.update(
                    productMarketLLMResponseDto.getName(),
                    productMarketLLMResponseDto.getBrandName(),
                    productMarketLLMResponseDto.getMadeInCountry(),
                    productMarketLLMResponseDto.getEnglishNetContent(),
                    productMarketLLMResponseDto.getKoreanNetContent(),
                    productMarketLLMResponseDto.getServingSize(),
                    productMarketLLMResponseDto.getEnglishProductType(),
                    productMarketLLMResponseDto.getKoreanProductType(),
                    productMarketLLMResponseDto.getEnglishSupplementForm(),
                    productMarketLLMResponseDto.getKoreanSupplementForm(),
                    productMarketLLMResponseDto.getEnglishSuggestedUse(),
                    productMarketLLMResponseDto.getKoreanSuggestedUse(),
                    productMarketLLMResponseDto.getEnglishOtherIngredients(),
                    productMarketLLMResponseDto.getKoreanOtherIngredients()
            );
        }

        // Ingredient 기존 DB에 있는 데이터 중 englishName 일치하는 ingredient 찾기
        // 없으면 새로 만들기
        List<ProductIngredientJoin> productIngredientJoinList = new ArrayList<>();
        for (ProductIngredientJoinLLMResponseDto productIngredientJoinLLMResponseDto : productMarketLLMResponseDto.getProductIngredientJoinLLMResponseDtoList()) {
            // Ingredient 찾기
            Ingredient ingredient = ingredientRepository
                    .findByEnglishName(productIngredientJoinLLMResponseDto.getEnglishName())
                    .orElse(null);

            // Ingredient 없으면 새로 만들기
            if (ingredient == null) {
                ingredient = Ingredient.of(
                        productIngredientJoinLLMResponseDto.getEnglishCategory(),
                        productIngredientJoinLLMResponseDto.getKoreanCategory(),
                        productIngredientJoinLLMResponseDto.getEnglishName(),
                        productIngredientJoinLLMResponseDto.getKoreanName(),
                        productIngredientJoinLLMResponseDto.getEffectiveness()
                );

                // Caution이 있으면 BanedIngredientInfo 추가
                if (productIngredientJoinLLMResponseDto.getCaution() != null) {
                    BanedIngredientInfo banedIngredientInfo = BanedIngredientInfo.of(
                            ingredient,
                            BanType.OTHER,
                            productIngredientJoinLLMResponseDto.getCaution()
                    );
                    ingredient.addBanedIngredientInfo(banedIngredientInfo);
                }
            }

            // ProductIngredientJoin 만들기
            ProductIngredientJoin productIngredientJoin = ProductIngredientJoin.of(
                    product,
                    ingredient,
                    productIngredientJoinLLMResponseDto.getServingSize(),
                    productIngredientJoinLLMResponseDto.getServingUnit(),
                    productIngredientJoinLLMResponseDto.getAmountPerServing(),
                    productIngredientJoinLLMResponseDto.getAmountPerServingUnit(),
                    productIngredientJoinLLMResponseDto.getDailyValuePerServing(),
                    productIngredientJoinLLMResponseDto.getEnglishDailyValueTargetGroup(),
                    productIngredientJoinLLMResponseDto.getKoreanDailyValueTargetGroup()
            );

            productIngredientJoinList.add(productIngredientJoin);
        }

        product.setProductIngredientJoinList(productIngredientJoinList);

        // ProductLabelStatement 만들기
        for (ProductLabelStatementLLMResponseDto productLabelStatementLLMResponseDto : productMarketLLMResponseDto.getProductLabelStatementLLMResponseDtoList()) {
            ProductLabelStatement productLabelStatement = ProductLabelStatement.of(
                    product,
                    StatementType.OTHER,
                    productLabelStatementLLMResponseDto.getEnglishLabelStatement(),
                    productLabelStatementLLMResponseDto.getKoreanLabelStatement()
            );
            product.addProductLabelStatement(productLabelStatement);
        }

        // ProductMarket 만들기
        ProductMarket productMarket = ProductMarket.of(
                product,
                productMarketLLMResponseDto.getImageUrl(),
                url,
                productMarketLLMResponseDto.getPrice(),
                productMarketLLMResponseDto.getPriceUnit()
        );

        // product에 productMarket 추가해주기
        product.addProductMarket(productMarket);

        // cascade로 인해 product, productIngredientJoin, productLabelStatement, productMarket, productIngredientJoin, ingredient 모두 저장됨
        return productMarketRepository.save(productMarket);
    }

}
