package com.cleanChoice.cleanChoice.domain.dataInput.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateIngredientRequestDto {

    @NotNull
    private String englishCategory;

    @NotNull
    private String koreanCategory;

    @NotNull
    private String ingredientName;

    @NotNull
    private String effectiveness;

    @NotNull
    private Boolean isClearanceBaned;

}
