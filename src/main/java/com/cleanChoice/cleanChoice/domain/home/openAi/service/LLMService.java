package com.cleanChoice.cleanChoice.domain.home.openAi.service;

import com.cleanChoice.cleanChoice.domain.home.openAi.dto.convert.ProductIngredientJoinLLMResponseDto;
import com.cleanChoice.cleanChoice.domain.home.openAi.dto.convert.ProductLabelStatementLLMResponseDto;
import com.cleanChoice.cleanChoice.domain.home.openAi.dto.convert.ProductMarketLLMResponseDto;
import com.cleanChoice.cleanChoice.domain.home.openAi.dto.OpenAiRequestDto;
import com.cleanChoice.cleanChoice.domain.home.openAi.dto.OpenAiResponseDto;
import com.cleanChoice.cleanChoice.domain.home.openAi.util.ResponseDtoText;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.BanType;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.BanedIngredientInfo;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.Ingredient;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.repository.IngredientRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.*;
import com.cleanChoice.cleanChoice.global.exceptions.BadRequestException;
import com.cleanChoice.cleanChoice.global.exceptions.ErrorCode;
import com.cleanChoice.cleanChoice.global.exceptions.InternalServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class LLMService {

    @Value("${openai.api_key}")
    private String apiKey;

    @Value("${openai.api_url}")
    private String apiUrl;

    private final IngredientRepository ingredientRepository;

    public ProductMarket packProductMarket(String marketUrl, Product product) {
        ProductMarketLLMResponseDto productMarketLLMResponseDto = this.getCompletion(marketUrl);

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
                productMarketLLMResponseDto.getImageUrl(),
                marketUrl,
                productMarketLLMResponseDto.getPrice(),
                productMarketLLMResponseDto.getPriceUnit()
        );

        // product에 productMarket 추가해주기
        product.addProductMarket(productMarket);

        return productMarket;
    }

    private ProductMarketLLMResponseDto getCompletion(String marketUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apiKey);
        headers.add("Content-Type", "application/json");

        OpenAiRequestDto.OpenAiMessageDto judgeMessage = this.buildMessage("user",
                "Is this url health functional food e-commerce product detail page?Answer Only Y/N\n" + marketUrl);
        OpenAiRequestDto.OpenAiMessageDto productMarketMessage = this.buildMessage("user",
                ResponseDtoText.PRODUCT_MARKET.getPrompt() + " URL:" + marketUrl + "\n" + ResponseDtoText.PRODUCT_MARKET.getText());
        OpenAiRequestDto.OpenAiMessageDto ingredientMessage = this.buildMessage("user",
                ResponseDtoText.PRODUCT_INGREDIENT_JOIN.getPrompt() + " URL:" + marketUrl + "\n" + ResponseDtoText.PRODUCT_INGREDIENT_JOIN.getText());
        OpenAiRequestDto.OpenAiMessageDto labelMessage = this.buildMessage("user",
                ResponseDtoText.PRODUCT_LABEL_STATEMENT.getPrompt() + " URL:" + marketUrl + "\n" + ResponseDtoText.PRODUCT_LABEL_STATEMENT.getText());

        String judgeResponse = requestToOpenAi(
                buildRequest(headers, "gpt-4o-mini", List.of(judgeMessage), 0.7)
        );

        if (!judgeResponse.equals("Y")) {
            throw new BadRequestException(ErrorCode.INVALID_PARAMETER, "This url is not health functional food e-commerce product detail page.");
        }

        String jsonProductMarketString = requestToOpenAi(
                buildRequest(headers, "gpt-4o", List.of(productMarketMessage), 0.7)
        );

        String jsonIngredientString = requestToOpenAi(
                buildRequest(headers, "gpt-4o", List.of(ingredientMessage), 0.7)
        );

        String jsonLabelString = requestToOpenAi(
                buildRequest(headers, "gpt-4o", List.of(labelMessage), 0.7)
        );

        jsonProductMarketString = jsonProductMarketString.replace("```json\n", "").replace("```", "");
        jsonIngredientString = jsonIngredientString.replace("```json\n", "").replace("```", "");
        jsonLabelString = jsonLabelString.replace("```json\n", "").replace("```", "");

        ProductMarketLLMResponseDto productMArketLLMREsponseDto = toProductMarketLLMResponseDto(
                jsonProductMarketString,
                jsonIngredientString,
                jsonLabelString
        );

        return validateProductMarketLLMResponseDto(productMArketLLMREsponseDto);
    }

    private OpenAiRequestDto.OpenAiMessageDto buildMessage(String role, String content) {
        return new OpenAiRequestDto.OpenAiMessageDto(role, content);
    }

    private HttpEntity<OpenAiRequestDto> buildRequest(HttpHeaders headers, String model, List<OpenAiRequestDto.OpenAiMessageDto> messages, double temperature) {

        return new HttpEntity<>(new OpenAiRequestDto(model, messages, temperature), headers);
    }

    private String requestToOpenAi(HttpEntity<OpenAiRequestDto> requestDtoHttpEntity) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OpenAiResponseDto> response = restTemplate.postForEntity(apiUrl, requestDtoHttpEntity, OpenAiResponseDto.class);
        Object responseBody = response.getBody();
        if (responseBody == null) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER, "Failed to get response from OpenAI.");
        }
        return response.getBody().getChoices().get(0).getMessage().getContent();
    }

    private ProductMarketLLMResponseDto toProductMarketLLMResponseDto(String jsonProductMarketString, String jsonIngredientString, String jsonLabelString) {
        /*
        log.info("jsonProductMarketString: {}", jsonProductMarketString);
        log.info("jsonIngredientString: {}", jsonIngredientString);
        log.info("jsonLabelString: {}", jsonLabelString);
         */

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parse product Json
            ProductMarketLLMResponseDto productMarketLLMResponseDto = objectMapper.readValue(jsonProductMarketString, ProductMarketLLMResponseDto.class);
            List<ProductIngredientJoinLLMResponseDto> productIngredientJoinLLMResponseDtoList = objectMapper.readValue(jsonIngredientString, objectMapper.getTypeFactory().constructCollectionType(List.class, ProductIngredientJoinLLMResponseDto.class));
            List<ProductLabelStatementLLMResponseDto> productLabelStatementLLMResponseDtoList = objectMapper.readValue(jsonLabelString, objectMapper.getTypeFactory().constructCollectionType(List.class, ProductLabelStatementLLMResponseDto.class));
            productMarketLLMResponseDto.setProductIngredientJoinLLMResponseDtoList(productIngredientJoinLLMResponseDtoList);
            productMarketLLMResponseDto.setProductLabelStatementLLMResponseDtoList(productLabelStatementLLMResponseDtoList);

            return productMarketLLMResponseDto;
        } catch (JsonProcessingException e) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER, e.toString());
        }
    }

    private ProductMarketLLMResponseDto validateProductMarketLLMResponseDto(ProductMarketLLMResponseDto productMarketLLMResponseDto) {
        if (productMarketLLMResponseDto == null) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER, "Failed to get product market information.");
        }

        Mono<Boolean> isImageUrlValid = validateImageUrl(productMarketLLMResponseDto.getImageUrl());

        if (productMarketLLMResponseDto.getPrice() != null ^ productMarketLLMResponseDto.getPriceUnit() != null) {
            productMarketLLMResponseDto.setPrice(null);
            productMarketLLMResponseDto.setPriceUnit(null);
        }

        if (isImageUrlValid.blockOptional().orElse(false)) {
            productMarketLLMResponseDto.setImageUrl(null);
        }

        return productMarketLLMResponseDto;
    }

    private Mono<Boolean> validateImageUrl(String imageUrl) {
        WebClient webClient = WebClient.create();
        return webClient.head()
                .uri(imageUrl)
                .exchangeToMono(clientResponse -> {
                    HttpStatusCode statusCode = clientResponse.statusCode();
                    String contentType = clientResponse.headers().contentType().map(MimeType::toString).orElse("");

                    return Mono.just(statusCode.is2xxSuccessful() && contentType.startsWith("image/"));
                }).onErrorResume(e -> Mono.just(false));
    }

}
