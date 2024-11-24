package com.cleanChoice.cleanChoice.domain.ingredient.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

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

    private LocalDateTime localDateTime;

    private LocalDateTime updatedAt;

}
