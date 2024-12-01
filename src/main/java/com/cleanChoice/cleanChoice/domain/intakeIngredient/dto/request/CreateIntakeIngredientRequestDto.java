package com.cleanChoice.cleanChoice.domain.intakeIngredient.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateIntakeIngredientRequestDto {

    @Schema(description = "성분 id 값", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @NotNull(message = "성분 id 값은 필수값입니다.")
    private Long ingredientId;

    @Schema(description = "복용량(nullable)", example = "100.0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Double amount;

    @Schema(description = "복용량 단위(nullable)", example = "mg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String unit;

}
