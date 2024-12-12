package com.cleanChoice.cleanChoice.domain.home.dto.response;

import com.cleanChoice.cleanChoice.domain.product.dto.response.AnalyzeType;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyzeResponseDto {

    @Schema(description = "상품 시장 id 값", example = "1")
    private Long id;

    @Schema(description = "상품 DTO")
    private ProductResponseDto productResponseDto;

    @Schema(description = "상품 시장 url", example = "https://www.amazon.com")
    private String url;

    @Schema(description = "상품 메인 이미지 url")
    private String imageUrl;

    @Schema(description = "상품 가격(수)", example = "10000")
    private Long price;

    @Schema(description = "상품 가격 단위", example = "USD")
    private String priceUnit;

    @Schema(description = "분석 타입(LLM_PARSED / DB_COSINE_DISTANCE)", example = "DB_COSINE_DISTANCE")
    private AnalyzeType analyzeType;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
