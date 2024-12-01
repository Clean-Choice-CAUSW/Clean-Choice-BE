package com.cleanChoice.cleanChoice.domain.home.openAi.service;

import com.cleanChoice.cleanChoice.domain.home.openAi.dto.convert.ProductIngredientJoinLLMResponseDto;
import com.cleanChoice.cleanChoice.domain.home.openAi.dto.convert.ProductLabelStatementLLMResponseDto;
import com.cleanChoice.cleanChoice.domain.home.openAi.dto.convert.ProductMarketLLMResponseDto;
import com.cleanChoice.cleanChoice.domain.home.openAi.dto.OpenAiRequestDto;
import com.cleanChoice.cleanChoice.domain.home.openAi.dto.OpenAiResponseDto;
import com.cleanChoice.cleanChoice.domain.home.openAi.util.ResponseDtoText;
import com.cleanChoice.cleanChoice.global.exceptions.BadRequestException;
import com.cleanChoice.cleanChoice.global.exceptions.ErrorCode;
import com.cleanChoice.cleanChoice.global.exceptions.InternalServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class LLMService {

    @Value("${openai.api_key}")
    private String apiKey;

    @Value("${openai.api_url}")
    private String apiUrl;

    public ProductMarketLLMResponseDto getCompletion(String marketUrl) {
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
        ).orElse("");

        if (!judgeResponse.equals("Y")) {
            throw new BadRequestException(ErrorCode.INVALID_PARAMETER, "This url is not health functional food e-commerce product detail page.");
        }

        String jsonProductMarketString = requestToOpenAi(
                buildRequest(headers, "gpt-4o", List.of(productMarketMessage), 0.7)
        ).orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER, "Failed to get product market information."));

        String jsonIngredientString = requestToOpenAi(
                buildRequest(headers, "gpt-4o", List.of(ingredientMessage), 0.7)
        ).orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER, "Failed to get product ingredient information."));

        String jsonLabelString = requestToOpenAi(
                buildRequest(headers, "gpt-4o", List.of(labelMessage), 0.7)
        ).orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER, "Failed to get product label information."));

        jsonProductMarketString = jsonProductMarketString.replace("```json\n", "").replace("```", "");
        jsonIngredientString = jsonIngredientString.replace("```json\n", "").replace("```", "");
        jsonLabelString = jsonLabelString.replace("```json\n", "").replace("```", "");

        return toProductMarketLLMResponseDto(
                jsonProductMarketString,
                jsonIngredientString,
                jsonLabelString
        );
    }

    private OpenAiRequestDto.OpenAiMessageDto buildMessage(String role, String content) {
        return new OpenAiRequestDto.OpenAiMessageDto(role, content);
    }

    private HttpEntity<OpenAiRequestDto> buildRequest(HttpHeaders headers, String model, List<OpenAiRequestDto.OpenAiMessageDto> messages, double temperature) {

        return new HttpEntity<>(new OpenAiRequestDto(model, messages, temperature), headers);
    }

    private Optional<String> requestToOpenAi(HttpEntity<OpenAiRequestDto> requestDtoHttpEntity) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OpenAiResponseDto> response = restTemplate.postForEntity(apiUrl, requestDtoHttpEntity, OpenAiResponseDto.class);
        Object responseBody = response.getBody();
        if (responseBody == null) {
            return Optional.empty();
        }
        return Optional.of(response.getBody().getChoices().get(0).getMessage().getContent());
    }

    private ProductMarketLLMResponseDto toProductMarketLLMResponseDto(String jsonProductMarketString, String jsonIngredientString, String jsonLabelString) {
        log.info("jsonProductMarketString: {}", jsonProductMarketString);
        log.info("jsonIngredientString: {}", jsonIngredientString);
        log.info("jsonLabelString: {}", jsonLabelString);

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

}
