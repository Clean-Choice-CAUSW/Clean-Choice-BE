package com.cleanChoice.cleanChoice.domain.dataInput.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCombineUserBanedIngredientRequestDto {

    @NotNull
    private String ingredientName;

    @NotNull
    private String combineIngredientName;

    @NotNull
    private String description;

}