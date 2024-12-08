package com.cleanChoice.cleanChoice.domain.dataInput.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductIngredientJoinRequestDto {

    @NotNull
    private Long dsldId;

    @NotNull
    private String ingredientName;

    @NotNull
    private String servingSize;

    @NotNull
    private String servingUnit;

    @NotNull
    private String amountPerServing;

    @NotNull
    private String amountPerServingUnit;

    @NotNull
    private String dailyValuePerServing;

    @NotNull
    private String englishDailyValueTargetGroup;

    @NotNull
    private String koreanDailyValueTargetGroup;

}
