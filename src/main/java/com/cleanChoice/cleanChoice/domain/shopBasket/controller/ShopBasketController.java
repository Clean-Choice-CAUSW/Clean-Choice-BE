package com.cleanChoice.cleanChoice.domain.shopBasket.controller;

import com.cleanChoice.cleanChoice.domain.shopBasket.domain.ShopBasketProductJoin;
import com.cleanChoice.cleanChoice.domain.shopBasket.dto.response.ShopBasketProductJoinResponseDto;
import com.cleanChoice.cleanChoice.domain.shopBasket.dto.response.ShopBasketResponseDto;
import com.cleanChoice.cleanChoice.domain.shopBasket.service.ShopBasketService;
import com.cleanChoice.cleanChoice.global.config.security.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shop-basket")
@RequiredArgsConstructor
public class ShopBasketController {

    private final ShopBasketService shopBasketService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "장바구니 생성", description = "장바구니를 생성합니다.")
    public ShopBasketResponseDto createShopBasket(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody String basketName
    ) {
        return shopBasketService.createShopBasket(customUserDetails.getMember(), basketName);
    }

    @PostMapping("/add-product")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "상품 추가", description = "장바구니에 상품을 추가합니다.")
    public ShopBasketProductJoinResponseDto addProduct(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestHeader Long shopBasketId,
            @RequestHeader Long productMarketId
    ) {
        return shopBasketService.addProduct(customUserDetails.getMember(), shopBasketId, productMarketId);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "장바구니 리스트", description = "장바구니 리스트를 조회합니다.")
    public List<ShopBasketResponseDto> getShopBasketList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return shopBasketService.getShopBasketList(customUserDetails.getMember());
    }

    @GetMapping("/{shopBasketId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "특정 장바구니 조회", description = "특정 장바구니를 조회합니다.")
    public ShopBasketResponseDto getShopBasket(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long shopBasketId
    ) {
        return shopBasketService.getShopBasket(customUserDetails.getMember(), shopBasketId);
    }

    @GetMapping("/product/{shopBasketProductJoinId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "장바구니의 특정 상품 조회", description = "장바구니의 특정 상품을 조회합니다.")
    public ShopBasketProductJoinResponseDto getShopBasketProduct(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long shopBasketProductJoinId
    ) {
        return shopBasketService.getShopBasketProduct(customUserDetails.getMember(), shopBasketProductJoinId);
    }

    @DeleteMapping("/delete-product")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "상품 삭제", description = "장바구니에 상품을 삭제합니다.")
    public ShopBasketProductJoinResponseDto deleteProduct(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestHeader Long shopBasketProductJoinId
    ) {
        return shopBasketService.deleteProduct(customUserDetails.getMember(), shopBasketProductJoinId);
    }

    @DeleteMapping("/delete-product/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "상품 전체 삭제", description = "장바구니에 있는 모든 상품을 삭제합니다.")
    public List<ShopBasketProductJoinResponseDto> deleteAllProduct(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestHeader Long shopBasketId
    ) {
        return shopBasketService.deleteAllProduct(customUserDetails.getMember(), shopBasketId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "장바구니 삭제", description = "장바구니를 삭제합니다.")
    public ShopBasketResponseDto deleteShopBasket(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestHeader Long shopBasketId
    ) {
        return shopBasketService.deleteShopBasket(customUserDetails.getMember(), shopBasketId);
    }

}
