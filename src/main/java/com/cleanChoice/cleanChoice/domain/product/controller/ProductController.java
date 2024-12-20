package com.cleanChoice.cleanChoice.domain.product.controller;

import com.cleanChoice.cleanChoice.domain.product.domain.ProductMarket;
import com.cleanChoice.cleanChoice.domain.product.dto.request.PersonalizedInfoRequestDto;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductMarketResponseDto;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductResponseDto;
import com.cleanChoice.cleanChoice.domain.product.service.ProductService;
import com.cleanChoice.cleanChoice.global.config.security.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product-market/{productMarketId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "마켓 등록된 상품 조회", description = "마켓 등록된 상품을 조회합니다. 조회시 조회 카운트가 증가합니다.")
    public ProductMarketResponseDto getProductMarket(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long productMarketId
    ) {
        return productService.getProductMarket(customUserDetails.getMember(), productMarketId);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "상품 조회", description = "상품을 조회합니다. 조회시 조회 카운트가 증가합니다.")
    public ProductResponseDto getProduct(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long productId
    ) {
        return productService.getProduct(customUserDetails.getMember(), productId);
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

    @PostMapping("/product-market/masking")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "사용자화 정보 마스킹 API", description = "사용자화 정보 마스킹 API")
    public ProductMarketResponseDto maskingProduct(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestHeader Long productMarketId,
            @RequestBody PersonalizedInfoRequestDto personalizedInfoRequestDto
    ) {
        return productService.makingProduct(customUserDetails.getMember(), productMarketId, personalizedInfoRequestDto);
    }

    @DeleteMapping("/product-market/masking")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "사용자화 정보 삭제 API", description = "사용자화 정보 삭제 API")
    public ProductMarketResponseDto deleteMaskingProduct(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestHeader Long productMarketId
    ) {
        return productService.deleteMaskingProduct(customUserDetails.getMember(), productMarketId);
    }

    // --------------- Data 등록 API(관리) ---------------
    @PostMapping(value = "/data", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "DB 상품 데이터 등록(FE 사용 X)", description = "DB 상품 데이터 등록(FE 사용 X)")
    public void insertProductData(
            @RequestPart(value = "overviewFile") MultipartFile overviewFile,
            @RequestPart(value = "ingredientFile") MultipartFile ingredientFile,
            @RequestPart(value = "labelFile") MultipartFile labelFile

    ) {
        //productService.insertProductData(file);
    }


}
