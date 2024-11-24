package com.cleanChoice.cleanChoice.domain.shopRecord.dto.response;

import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductMarketResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopRecordResponseDto {

    @Schema(description = "쇼핑 기록 id 값", example = "1")
    private Long id;

    @Schema(description = "Member id 값", example = "1")
    private Long memberId;

    @Schema(description = "상품-마켓 DTO")
    private ProductMarketResponseDto productMarketResponseDto;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
