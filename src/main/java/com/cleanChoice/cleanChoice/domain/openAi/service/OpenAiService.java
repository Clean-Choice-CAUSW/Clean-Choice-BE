package com.cleanChoice.cleanChoice.domain.openAi.service;

import com.cleanChoice.cleanChoice.domain.home.dto.request.AnalyzeRequestDto;
import com.cleanChoice.cleanChoice.domain.member.domain.Gender;
import com.cleanChoice.cleanChoice.domain.openAi.dto.convert.ProductIngredientJoinLLMResponseDto;
import com.cleanChoice.cleanChoice.domain.openAi.dto.convert.ProductLabelStatementLLMResponseDto;
import com.cleanChoice.cleanChoice.domain.openAi.dto.convert.ProductMarketLLMResponseDto;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.BanType;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.BanedIngredientInfo;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.Ingredient;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.repository.IngredientRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAiService {

    @Value("${openai.api_key}")
    private String apiKey;

    @Value("${openai.api_url}")
    private String apiUrl;

    @Value("${flask.completion-url}")
    private String flaskCompletionUrl;

    private final WebClient webClient;

    private final IngredientRepository ingredientRepository;

    public ProductMarket packProductMarket(AnalyzeRequestDto analyzeRequestDto, Product product) {
        ProductMarketLLMResponseDto productMarketLLMResponseDto = this.getCompletion(analyzeRequestDto);

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
                    .orElse(
                            ingredientRepository.findByKoreanName(productIngredientJoinLLMResponseDto.getKoreanName())
                                    .orElse(null)
                    );

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
                analyzeRequestDto.getImageUrl() != null ? analyzeRequestDto.getImageUrl() : productMarketLLMResponseDto.getImageUrl(),
                analyzeRequestDto.getUrl(),
                analyzeRequestDto.getPrice() != null ? analyzeRequestDto.getPrice() : productMarketLLMResponseDto.getPrice(),
                analyzeRequestDto.getPriceUnit() != null ? analyzeRequestDto.getPriceUnit() : productMarketLLMResponseDto.getPriceUnit()
        );

        // product에 productMarket 추가해주기
        product.addProductMarket(productMarket);

        return productMarket;
    }

    public ProductMarketLLMResponseDto getCompletion(AnalyzeRequestDto analyzeRequestDto) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("html", analyzeRequestDto.getHtml());
        requestBody.put("imageUrlList", analyzeRequestDto.getImageUrlList());

        // Flask API 호출 및 응답 매핑
        return webClient.post()
                .uri( flaskCompletionUrl + "/gen-completion")
                .bodyValue(requestBody) // 요청 본문 설정
                .retrieve()
                .bodyToMono(ProductMarketLLMResponseDto.class) // 응답을 DTO로 변환
                .block(); // 동기 처리
    }

    public String getAdvice(
            Integer age,
            Gender gender,
            Boolean isPregnant,
            String intakeIngredientListString,
            String selectIngredientListString,
            String question
    ) {
        if (intakeIngredientListString == null) intakeIngredientListString = "";
        if (selectIngredientListString == null) selectIngredientListString = "";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("age", age);
        requestBody.put("gender", gender);
        requestBody.put("isPregnant", isPregnant);
        requestBody.put("intakeIngredientListString", intakeIngredientListString);
        requestBody.put("selectIngredientListString", selectIngredientListString);
        requestBody.put("question", question);

        return webClient.post()
                .uri(flaskCompletionUrl + "/get-advice")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
