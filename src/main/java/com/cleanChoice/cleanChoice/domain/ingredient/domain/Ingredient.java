package com.cleanChoice.cleanChoice.domain.ingredient.domain;

import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "ingredient")
public class Ingredient extends BaseEntity {

    @Column(name = "english_category", nullable = true)
    private String englishCategory;

    @Column(name = "korean_category", nullable = true)
    private String koreanCategory;

    @Setter(value = AccessLevel.PUBLIC)
    @Column(name = "name", nullable = true)
    private String englishName;

    @Setter(value = AccessLevel.PUBLIC)
    @Column(name = "korean_name", nullable = true)
    private String koreanName;

    // 효능
    @Column(name = "effectiveness", nullable = true)
    private String effectiveness;

    // 통관 금지 여부
    @Column(name = "is_clearance_baned", nullable = false)
    @Builder.Default
    private Boolean isClearanceBaned = false;

    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<BanedIngredientInfo> banedIngredientInfoList;

    public static Ingredient of(String englishName, String koreanName) {
        return Ingredient.builder()
                .englishName(englishName)
                .koreanName(koreanName)
                .build();
    }

    public static Ingredient englishNameOf(String englishName) {
        return Ingredient.builder()
                .englishName(englishName)
                .build();
    }

    public static Ingredient koreanNameOf(String koreanName) {
        return Ingredient.builder()
                .koreanName(koreanName)
                .build();
    }

}
