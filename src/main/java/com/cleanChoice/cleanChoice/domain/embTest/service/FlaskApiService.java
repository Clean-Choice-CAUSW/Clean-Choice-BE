package com.cleanChoice.cleanChoice.domain.embTest.service;

import com.cleanChoice.cleanChoice.domain.embTest.dto.EmbRequestDto;
import com.cleanChoice.cleanChoice.domain.embTest.dto.EmbResponseDto;
import com.cleanChoice.cleanChoice.global.exceptions.ErrorCode;
import com.cleanChoice.cleanChoice.global.exceptions.InternalServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FlaskApiService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${flask.url}")
    private String flaskUrl;

    public Map<String, List<Float>> getEmbeddingVectors(String productName, String brandName) {
        // 요청 데이터 생성
        Map<String, String> requestBody = Map.of(
                "productName", productName,
                "brandName", brandName
        );

        // HTTP 헤더 설정 (JSON 형식)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // 요청 생성
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Flask API 호출
        ResponseEntity<Map> response = restTemplate.exchange(
                flaskUrl,
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        // 응답 처리
        Map<String, Object> rawResponse = response.getBody();
        if (rawResponse == null) {
            throw new RuntimeException("Empty response from Flask API");
        }

        // 응답 데이터를 Map<String, List<Float>>로 변환
        return rawResponse.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            if (!(entry.getValue() instanceof List)) {
                                throw new RuntimeException("Invalid response format");
                            }
                            return (List<Float>) entry.getValue(); // 형변환
                        }
                ));
    }

    public List<EmbResponseDto> getEmbeddingVectorList(List<EmbRequestDto> embRequestDtoList) {
        // HTTP 헤더 설정 (JSON 형식)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // 요청 데이터와 헤더 생성
        HttpEntity<List<EmbRequestDto>> requestEntity = new HttpEntity<>(embRequestDtoList, headers);

        // Flask API 호출
        ResponseEntity<EmbResponseDto[]> response = restTemplate.exchange(
                flaskUrl,
                HttpMethod.POST,
                requestEntity,
                EmbResponseDto[].class // 응답 형식을 배열로 처리
        );

        // 배열을 List로 변환하여 반환
        if (response.getBody() == null) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER, "Empty response from Flask API");
        }

        return Arrays.asList(response.getBody());
    }

    public EmbResponseDto getEmbeddingVector(String productName, String brandName) {
        EmbRequestDto embRequestDto = EmbRequestDto.builder()
                .productName(productName)
                .brandName(brandName)
                .build();
        EmbResponseDto embResponseDto = getEmbeddingVectorList(List.of(embRequestDto)).get(0);
        if (embResponseDto == null) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER, "Empty response from Flask API");
        }
        return embResponseDto;
    }

}