package com.cleanChoice.cleanChoice.domain.ingredient.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchIngredientRequestDto {

    private String ingredientName;

}
