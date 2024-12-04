package com.cleanChoice.cleanChoice.domain.product.controller;

import com.cleanChoice.cleanChoice.domain.product.dto.request.PersonalizedInfoRequestDto;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductResponseDto;
import com.cleanChoice.cleanChoice.domain.product.service.ProductService;
import com.cleanChoice.cleanChoice.global.config.security.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productMarketId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "마켓 등록된 상품 조회", description = "상품을 조회합니다. 조회시 조회 카운트가 증가합니다.")
    public ProductResponseDto getProductMarket(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long productMarketId
    ) {
        return productService.getProduct(customUserDetails.getMember(), productMarketId);
    }

    @GetMapping("/search/name")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "상품 이름으로 검색", description = "상품 이름으로 상품을 검색합니다.")
    public ProductResponseDto searchProductByName(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam String name
    ) {
        return productService.searchProductByName(customUserDetails.getMember(), name);
    }

    @PostMapping("/masking")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "사용자화 정보 마스킹 API", description = "사용자화 정보 마스킹 API")
    public ProductResponseDto maskingProduct(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestHeader Long productMarketId,
            @RequestBody PersonalizedInfoRequestDto personalizedInfoRequestDto
    ) {
        return productService.makingProduct(customUserDetails.getMember(), productMarketId, personalizedInfoRequestDto);
    }

    @DeleteMapping("/masking")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "사용자화 정보 삭제 API", description = "사용자화 정보 삭제 API")
    public ProductResponseDto deleteMaskingProduct(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestHeader Long productMarketId
    ) {
        return productService.deleteMaskingProduct(customUserDetails.getMember(), productMarketId);
    }

}
