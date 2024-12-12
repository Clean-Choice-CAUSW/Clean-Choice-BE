package com.cleanChoice.cleanChoice.domain.dataInput.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateIngredientEffectivenessRequestDto {

    private String ingredientName;

    private String effectiveness;

}
