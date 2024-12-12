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

}
