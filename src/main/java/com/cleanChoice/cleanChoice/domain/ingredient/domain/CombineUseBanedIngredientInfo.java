package com.cleanChoice.cleanChoice.domain.ingredient.domain;

import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "combine_use_baned_ingredient",
indexes = {
        @Index(name = "cubi_ingredient_idx", columnList = "ingredient_id"),
        @Index(name ="cubi_combine_ingredient_idx", columnList = "combine_ingredient_id")
})
public class CombineUseBanedIngredientInfo extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combine_ingredient_id", nullable = false)
    private Ingredient combineIngredient;

    @Column(name = "description", nullable = true)
    private String description;

    public static CombineUseBanedIngredientInfo of(
            Ingredient ingredient,
            Ingredient combineIngredient,
            String description
    ) {
        return CombineUseBanedIngredientInfo.builder()
                .ingredient(ingredient)
                .combineIngredient(combineIngredient)
                .description(description)
                .build();
    }

}
