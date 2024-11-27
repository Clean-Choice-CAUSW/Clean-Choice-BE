package com.cleanChoice.cleanChoice.domain.product.controller;

import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductResponseDto;
import com.cleanChoice.cleanChoice.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
            @PathVariable String productMarketId
    ) {
        return productService.getProduct(productMarketId);
    }

}
