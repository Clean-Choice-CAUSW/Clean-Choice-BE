package com.cleanChoice.cleanChoice.domain.ingredient.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientResponseDto {

    @Schema(description = "Ingredient id 값", example = "1")
    private Long id;

    @Schema(description = "Ingredient 카테고리 / 영어", example = "Vitamin")
    private String englishCategory;

    @Schema(description = "Ingredient 카테고리 / 한글", example = "비타민")
    private String koreanCategory;

    @Schema(description = "Ingredient 이름 / 영어", example = "Vitamin A")
    private String englishName;

    @Schema(description = "Ingredient 이름 / 한글", example = "비타민 A")
    private String koreanName;

    @Schema(description = "Ingredient 효능", example = "피부건강에 좋음.")
    private String effectiveness;

    @Schema(description = "통관 금지 여부", example = "false")
    private Boolean isClearanceBaned;

    @Schema(description = "금지/주의 정보 Dto 리스트(없을 시 빈 리스트)")
    private List<BanedIngredientInfoResponseDto> banedIngredientInfoResponseDtoList;

    @Schema(description = "조합 금지 정보 Dto 리스트(없을 시 빈 리스트)")
    private List<CombineUseBanedIngredientInfoResponseDto> combineUseBanedIngredientInfoResponseDtoList;

    private LocalDateTime localDateTime;

    private LocalDateTime updatedAt;

}
