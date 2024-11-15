package com.cleanChoice.cleanChoice.domain.product.domain;

import com.cleanChoice.cleanChoice.domain.ingredient.domain.Ingredient;
import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "product_ingredient")
public class ProductIngredientJoin extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(name = "amount_per_serving", nullable = true)
    private Double amountPerServing;

    @Column(name = "amount_per_serving_unit", nullable = true)
    private String amountPerServingUnit;

    @Column(name = "daily_value_target_group", nullable = true)
    private Double dailyValueTargetGroup;

    public static ProductIngredientJoin of() {
        return ProductIngredientJoin.builder()
                .build();
    }

}
