package com.cleanChoice.cleanChoice.domain.intakeIngredient.dto.response;

import com.cleanChoice.cleanChoice.domain.ingredient.dto.response.IngredientResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntakeIngredientResponseDto {

    @Schema(description = "복용 중 성분 id 값", example = "1")
    private Long id;

    @Schema(description = "Member id 값", example = "1")
    private Long memberId;

    @Schema(description = "Ingredient DTO")
    private IngredientResponseDto ingredientResponseDto;

    @Schema(description = "복용량", example = "100.0")
    private Double amount;

    @Schema(description = "복용량 단위", example = "mg")
    private String unit;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
