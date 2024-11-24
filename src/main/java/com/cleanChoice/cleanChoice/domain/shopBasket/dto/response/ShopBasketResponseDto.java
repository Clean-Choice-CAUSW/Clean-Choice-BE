package com.cleanChoice.cleanChoice.domain.shopBasket.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopBasketResponseDto {

    @Schema(description = "장바구니 id 값", example = "1")
    private Long id;

    @Schema(description = "Member id 값", example = "1")
    private Long memberId;

    @Schema(description = "장바구니 이름", example = "장바구니")
    private String name;

    @Schema(description = "장바구니에 담긴 상품 리스트 DTO")
    private List<ShopBasketProductJoinResponseDto> shopBasketProductJoinResponseDtoList;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
