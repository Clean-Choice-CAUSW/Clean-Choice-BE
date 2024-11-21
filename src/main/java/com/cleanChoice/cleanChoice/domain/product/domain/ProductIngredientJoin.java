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

    // 1회 복용양(ex: 1)
    @Column(name = "serving_size", nullable = true)
    private Double servingSize;

    // 1회 복용양 단위(ex: Tablet(s))
    @Column(name = "serving_unit", nullable = true)
    private String servingUnit;

    // 복용 당 성분 섭취량(ex: 1000)
    @Column(name = "amount_per_serving", nullable = true)
    private Double amountPerServing;

    // 복용 당 성분 섭취량 단위(ex: mg)
    @Column(name = "amount_per_serving_unit", nullable = true)
    private String amountPerServingUnit;

    // 일일 권장 섭취량 대비 비율(%, ex: 5882)
    @Column(name = "daily_value_per_serving", nullable = true)
    private Double dailyValuePerServing;

    // 일일 권장 섭취량 기준 그룹(ex: Adults and children 4 or more years of age)
    @Column(name = "daily_value_target_group", nullable = true)
    private String englishDailyValueTargetGroup;

    @Column(name = "korean_daily_value_target_group", nullable = true)
    private String koreanDailyValueTargetGroup;

    public static ProductIngredientJoin of() {
        return ProductIngredientJoin.builder()
                .build();
    }

}
