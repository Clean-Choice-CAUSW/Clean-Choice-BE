package com.cleanChoice.cleanChoice.domain.ingredient.domain;

import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "pregnant_baned_ingredient",
indexes = {
        @Index(name = "bi_ingredient_idx", columnList = "ingredient_id")
})
public class BanedIngredientInfo extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Enumerated(EnumType.STRING)
    @Column(name = "ban_type", nullable = false)
    private BanType banType;

    @Column(name = "description", nullable = true)
    private String description;

    public static BanedIngredientInfo of(
            Ingredient ingredient,
            BanType banType,
            String description
    ) {
        return BanedIngredientInfo.builder()
                .ingredient(ingredient)
                .banType(banType)
                .description(description)
                .build();
    }

}
