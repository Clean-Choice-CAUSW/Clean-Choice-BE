package com.cleanChoice.cleanChoice.domain.viewRecord.dto.response;

import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductMarketResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewRecordResponseDto {

    @Schema(description = "상품 시장 id 값", example = "1")
    private Long id;

    @Schema(description = "Member id 값", example = "1")
    private Long memberId;

    @Schema(description = "상품-마켓 DTO")
    private ProductMarketResponseDto productMarketResponseDto;

    private String createdAt;

    private String updatedAt;

}
