package com.cleanChoice.cleanChoice.domain.ingredient.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CombineUseBanedIngredientInfoResponseDto {

    @Schema(description = "조합 금지 정보 id 값", example = "1")
    private Long id;

    @Schema(description = "금지/주의 정보가 적용된 ingredient id 값", example = "1")
    private Long ingredientId;

    @Schema(description = "금지/주의 정보가 적용된 ingredient 영어 이름(영어 / 한글 둘 중 하나 null일 수도 있음)", example = "Vitamin A")
    private String ingredientEnglishName;

    @Schema(description = "금지/주의 정보가 적용된 ingredient 한글 이름(영어 / 한글 둘 중 하나 null일 수도 있음)", example = "비타민 A")
    private String ingredientKoreanName;

    @Schema(description = "금지/주의 정보가 적용된 조합 ingredient id 값", example = "1")
    private Long combineIngredientId;

    @Schema(description = "금지/주의 정보가 적용된 조합 ingredient 영어 이름(영어 / 한글 둘 중 하나 null일 수도 있음)", example = "Vitamin B")
    private String combineIngredientEnglishName;

    @Schema(description = "금지/주의 정보가 적용된 조합 ingredient 한글 이름(영어 / 한글 둘 중 하나 null일 수도 있음)", example = "비타민 B")
    private String combineIngredientKoreanName;

    @Schema(description = "금지/주의 정보 상세 설명", example = "항콜린성 작용이 있는 약제와의 병용으로 인해 항콜린 부작용이 증가하므로 동시 사용을 추천하지 않음")
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
