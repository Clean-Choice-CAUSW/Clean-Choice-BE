package com.cleanChoice.cleanChoice.domain.home.presentation;

import com.cleanChoice.cleanChoice.domain.home.dto.request.HomeRequestDto;
import com.cleanChoice.cleanChoice.domain.home.dto.response.HomeResponseDto;
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
    @Operation(summary = "상품 URL 분석", description = "상품 URL을 분석합니다.")
    public HomeResponseDto analyze(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid HomeRequestDto homeRequestDto
    ) {
        return null;
    }


}
