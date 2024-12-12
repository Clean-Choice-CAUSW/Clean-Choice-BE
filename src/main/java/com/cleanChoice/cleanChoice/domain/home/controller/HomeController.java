package com.cleanChoice.cleanChoice.domain.home.controller;

import com.cleanChoice.cleanChoice.domain.home.dto.response.AnalyzeResponseDto;
import com.cleanChoice.cleanChoice.domain.home.service.HomeService;
import com.cleanChoice.cleanChoice.domain.home.dto.request.AnalyzeRequestDto;
import com.cleanChoice.cleanChoice.domain.openAi.dto.convert.ProductMarketLLMResponseDto;
import com.cleanChoice.cleanChoice.domain.openAi.service.OpenAiService;
import com.cleanChoice.cleanChoice.domain.product.dto.response.AnalyzeType;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductMarketResponseDto;
import com.cleanChoice.cleanChoice.global.config.security.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;
    private final OpenAiService openAiService;

    /*
    1. Product Market 데이터 비교 후 url 완전 일치 시 return
        - 이때 이미 있던 Product Market 수정한 Product Market 객체 임시로 만들고 DB 저장은 X, 해당 값 10분(?) 간 redis에 캐싱
        product Market id
        user id
        new product name
        new brand name
        new price
        new price unit
        view count + 1

        - 이후 맞을 시 productName, brandName, price, priceUnit 다를 시 update
     */
    @PostMapping("/analyze")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "상품 URL DB 분석", description = "상품 URL을 DB 데이터와 비교해 분석합니다." +
            "Response의 AnalyzeType이 DB_COSINE_DISTANCE일 경우 맞았는지 틀렸는지 사용자 선택을 \"/api/v1/home/result-correct\"를 다시 보내야 합니다." +
            "AnalyzeType이 LLM_PARSED일 경우 다시 보내면 안 됩니다.")
    public AnalyzeResponseDto analyzeWithDB(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid AnalyzeRequestDto homeRequestDto
    ) {
        return homeService.analyze(customUserDetails.getMember(), homeRequestDto);
    }

    /*
        1. 맞을 시
            Redis에 캐싱되어 있던 객체 DB에 저장
            Redis 캐시 삭제

        2. 틀릴 시
            Product name, brand name 임베딩 값 cos 유사도 비교 후 return
                product
                product market
            바로 저장

        이후 둘 중 하나 최근 본 기록에 저장
     */
    @PostMapping("/result-correct")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "상품 URL DB 매치시 보내는 API",
            description = "상품 URL이 DB에 매치 후 맞았는지 틀렸는지 사용자의 선택 결과 재전송하는 API 입니다." +
                    "사용자 선택이 틀렸다고 하면 LLM 분석 후 DB에 저장합니다. DB_ANALYZE, DB_MAKE일 경우에만 보내야 합니다.")
    public ProductMarketResponseDto resultCorrect(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestHeader Long productMarketId,
            @RequestHeader Boolean isCorrect,
            @RequestHeader AnalyzeType analyzeType,
            @RequestBody @Valid AnalyzeRequestDto analyzeRequestDto
            ) {
        return homeService.resultCorrect(customUserDetails.getMember(), productMarketId, isCorrect, analyzeType, analyzeRequestDto);
    }

    @GetMapping("/test-completion")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "test api 입니다. 사용X")
    public ProductMarketLLMResponseDto testCompletion(
            //@RequestBody @Valid AnalyzeRequestDto analyzeRequestDto
    ) {
        String htmlContent = "<img alt=\"Centrum Silver Men&amp;#39;s 50+ Multivitamin with Vitamin D3, B-Vitamins, Zinc for Memory and Cognition - 200 Tablets\" src=\"https://m.media-amazon.com/images/I/71kh-hE7hVL._AC_SX569_.jpg\" data-old-hires=\"https://m.media-amazon.com/images/I/71kh-hE7hVL._AC_SL1500_.jpg\" onload=\"markFeatureRenderForImageBlock(); if(this.width/this.height > 1.0){this.className += ' a-stretch-horizontal'}else{this.className += ' a-stretch-vertical'};this.onload='';setCSMReq('af');if(typeof addlongPoleTag === 'function'){ addlongPoleTag('af','desktop-image-atf-marker');};setCSMReq('cf')\" data-a-image-name=\"landingImage\" class=\"a-dynamic-image a-stretch-vertical\" id=\"landingImage\" data-a-dynamic-image=\"{&quot;https://m.media-amazon.com/images/I/71kh-hE7hVL._AC_SX466_.jpg&quot;:[466,466],&quot;https://m.media-amazon.com/images/I/71kh-hE7hVL._AC_SX425_.jpg&quot;:[425,425],&quot;https://m.media-amazon.com/images/I/71kh-hE7hVL._AC_SX522_.jpg&quot;:[522,522],&quot;https://m.media-amazon.com/images/I/71kh-hE7hVL._AC_SX679_.jpg&quot;:[679,679],&quot;https://m.media-amazon.com/images/I/71kh-hE7hVL._AC_SY450_.jpg&quot;:[450,450],&quot;https://m.media-amazon.com/images/I/71kh-hE7hVL._AC_SX569_.jpg&quot;:[569,569],&quot;https://m.media-amazon.com/images/I/71kh-hE7hVL._AC_SY355_.jpg&quot;:[355,355]}\" style=\"max-width: 276.427px; max-height: 568px;\"><span id=\"productTitle\" class=\"a-size-large product-title-word-break\">Centrum Silver Men's 50+ Multivitamin with Vitamin D3, B-Vitamins, Zinc for Memory and Cognition - 200 Tablets</span>";

        AnalyzeRequestDto analyzeRequestDto1 = AnalyzeRequestDto.builder()
                .html(htmlContent)
                .imageUrlList(List.of())
                .build();
        return openAiService.getCompletion(analyzeRequestDto1);
    }

}
