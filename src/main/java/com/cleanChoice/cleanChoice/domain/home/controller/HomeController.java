package com.cleanChoice.cleanChoice.domain.home.controller;

import com.cleanChoice.cleanChoice.domain.home.service.HomeService;
import com.cleanChoice.cleanChoice.domain.home.dto.request.AnalyzeRequestDto;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductMarketResponseDto;
import com.cleanChoice.cleanChoice.global.config.security.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

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
            "맞았는지 틀렸는지 사용자 선택을 \"/api/v1/home/result-correct\"를 다시 보내야 합니다.")
    public ProductMarketResponseDto analyzeWithDB(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid AnalyzeRequestDto homeRequestDto
    ) {
        return homeService.analyzeWithDB(customUserDetails.getMember(), homeRequestDto);
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
                    "사용자 선택이 틀렸다고 하면 LLM 분석 후 DB에 저장합니다.")
    public ProductMarketResponseDto resultCorrect(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestHeader Long productMarketId,
            @RequestHeader Boolean isCorrect
    ) {
        return homeService.resultCorrect(customUserDetails.getMember(), productMarketId, isCorrect);
    }

}
