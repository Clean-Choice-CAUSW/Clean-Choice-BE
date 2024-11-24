package com.cleanChoice.cleanChoice.domain.shopBasket.dto.response;

import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductMarketResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopBasketProductJoinResponseDto {

    @Schema(description = "장바구니 상품 조인 id 값", example = "1")
    private Long id;

    @Schema(description = "상품 id 값", example = "1")
    private Long shopBasketId;

    @Schema(description = "상품-마켓 DTO")
    private ProductMarketResponseDto productMarketResponseDto;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
