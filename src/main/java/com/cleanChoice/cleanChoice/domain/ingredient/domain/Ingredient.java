package com.cleanChoice.cleanChoice.domain.ingredient.domain;

import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

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

    // TODO: 성분 별 주의 사항 관련 특성 제작 필요

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
