package com.cleanChoice.cleanChoice.domain.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductMarketResponseDto {

    @Schema(description = "상품 시장 id 값", example = "1")
    private Long id;

    @Schema(description = "상품 DTO")
    private ProductResponseDto productResponseDto;

    @Schema(description = "상품 시장 url", example = "https://www.amazon.com")
    private String url;

    @Schema(description = "상품 가격(수)", example = "10000")
    private Long price;

    @Schema(description = "상품 가격 단위", example = "USD")
    private String priceUnit;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
