package com.cleanChoice.cleanChoice.domain.dataInput.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBanedIngredientRequestDto {

    @NotNull
    private String ingredientName;

    @NotNull
    private String banType;

    @NotNull
    private String description;

}
