package com.cleanChoice.cleanChoice.domain.ingredient.dto.response;

import com.cleanChoice.cleanChoice.domain.ingredient.domain.BanType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BanedIngredientInfoResponseDto {

    @Schema(description = "금지/주의 정보 id 값", example = "1")
    private Long id;

    @Schema(description = "금지/주의 정보가 적용된 ingredient id 값", example = "1")
    private Long ingredientId;

    @Schema(description = "금지/주의 정보가 타입(PREGNANT_BANED / OLD_CAUTION)", example = "OLD_CAUTION")
    private BanType banType;

    @Schema(description = "금지/주의 정보가 타입(PREGNANT_BANED / OLD_CAUTION) 한글 설명", example = "노인 주의")
    private String banTypeDescription;

    @Schema(description = "금지/주의 정보 상세 설명", example = "항콜린성 작용이 있는 약제와의 병용으로 인해 항콜린 부작용이 증가하므로 동시 사용을 추천하지 않음")
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
