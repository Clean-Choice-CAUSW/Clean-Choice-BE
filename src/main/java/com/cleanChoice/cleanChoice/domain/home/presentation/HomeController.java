package com.cleanChoice.cleanChoice.domain.home.presentation;

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

    @GetMapping("/analyze")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "상품 URL DB 분석", description = "상품 URL을 DB 데이터와 비교해 분석합니다.")
    public ProductMarketResponseDto analyzeWithDB(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid AnalyzeRequestDto homeRequestDto
    ) {
        return null;
    }

}
