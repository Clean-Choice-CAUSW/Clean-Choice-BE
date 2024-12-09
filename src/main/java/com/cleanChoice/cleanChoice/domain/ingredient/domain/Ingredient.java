package com.cleanChoice.cleanChoice.domain.ingredient.domain;

import com.cleanChoice.cleanChoice.domain.dataInput.dto.CreateIngredientRequestDto;
import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "ingredient",
indexes = {
        @Index(name = "idx_english_name", columnList = "name")
})
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

    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
    @Builder.Default
    private List<BanedIngredientInfo> banedIngredientInfoList = new ArrayList<>();

    public static Ingredient of(
            String englishCategory,
            String koreanCategory,
            String englishName,
            String koreanName,
            String effectiveness
    ) {
        return Ingredient.builder()
                .englishCategory(englishCategory)
                .koreanCategory(koreanCategory)
                .englishName(englishName)
                .koreanName(koreanName)
                .effectiveness(effectiveness)
                .build();
    }

    public static Ingredient from(CreateIngredientRequestDto createIngredientRequestDto) {
        return Ingredient.builder()
                .englishCategory(createIngredientRequestDto.getEnglishCategory())
                .koreanCategory(createIngredientRequestDto.getKoreanCategory())
                .englishName(createIngredientRequestDto.getIngredientName())
                .effectiveness(createIngredientRequestDto.getEffectiveness())
                .isClearanceBaned(createIngredientRequestDto.getIsClearanceBaned())
                .build();
    }

    public void updateFrom(CreateIngredientRequestDto createIngredientRequestDto) {
        this.englishCategory = Objects.equals(createIngredientRequestDto.getEnglishCategory(), "") ? this.englishCategory : createIngredientRequestDto.getEnglishCategory();
        this.koreanCategory = Objects.equals(createIngredientRequestDto.getKoreanCategory(), "") ? this.koreanCategory : createIngredientRequestDto.getKoreanCategory();
        this.englishName = Objects.equals(createIngredientRequestDto.getIngredientName(), "") ? this.englishName : createIngredientRequestDto.getIngredientName();
        this.effectiveness = Objects.equals(createIngredientRequestDto.getEffectiveness(), "") ? this.effectiveness : createIngredientRequestDto.getEffectiveness();
        this.isClearanceBaned = Objects.equals(createIngredientRequestDto.getIsClearanceBaned(), null) ? this.isClearanceBaned : createIngredientRequestDto.getIsClearanceBaned();
    }

    public void updateIsClearanceBaned(Boolean isClearanceBaned) {
        this.isClearanceBaned = isClearanceBaned;
    }

    public void addBanedIngredientInfo(BanedIngredientInfo banedIngredientInfo) {
        this.banedIngredientInfoList.add(banedIngredientInfo);
    }

}
