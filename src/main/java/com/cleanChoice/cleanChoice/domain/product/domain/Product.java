package com.cleanChoice.cleanChoice.domain.product.domain;

import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "product")
public class Product extends BaseEntity {

    // dsld_id
    @Column(name = "dsld_id", nullable = true)
    private Long dsldId;

    // dsld 제품 상세 페이지 URL
    @Column(name = "dsld_url", nullable = true)
    private String dsldUrl;

    // 각 마켓 제품 상세 페이지 URL
    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @Column(name = "market_url", nullable = false)
    private Set<String> marketUrlList;

    // 제품 영어명
    @Column(name = "english_name", nullable = true)
    private String englishName;

    // 제품 한글명
    @Column(name = "korean_name", nullable = true)
    private String koreanName;

    // 유효 성분 리스트
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductIngredientJoin> productIngredientJoinList;

    // 제조사명 (ex: Vitamin World)
    @Column(name = "brand_name", nullable = true)
    private String brandName;

    // 제품 총량 (ex: 100 Easy To Swallow Coated Tablet(s))
    @Column(name = "english_net_content", nullable = true)
    private String englishNetContent;

    @Column(name = "korean_net_content", nullable = true)
    private String koreanNetContent;

    // 1회 제공량 (ex: 1 tsp [3 g-9 g])
    @Column(name = "serving_size", nullable = true)
    private String servingSize;

    // 제품 종류(LangauL) (ex: Non-Nutrient/Non-Botanical [A1309])
    @Column(name = "english_product_type", nullable = true)
    private String englishProductType;

    @Column(name = "korean_product_type", nullable = true)
    private String koreanProductType;

    // 섭취 형태(LanguaL) (ex: Tablet or Pill [E0155])
    @Column(name = "english_supplement_form", nullable = true)
    private String englishSupplementForm;

    @Column(name = "korean_supplement_form", nullable = true)
    private String koreanSupplementForm;

    // 권장 복용법 (ex: DIRECTIONS: For adults, take one (1) to two (2) tablets daily, preferably with a meal.)
    @Column(name = "english_suggested_use", nullable = true)
    private String englishSuggestedUse;

    @Column(name = "korean_suggested_use", nullable = true)
    private String koreanSuggestedUse;

    // 이외 성분 (ex: Dicalcium Phosphate, Vegetable Cellulose, Croscarmellose, Vegetable Magnesium Stearate, Silica, Vegetable Cellulose Coating)
    @Column(name = "english_other_ingredients", nullable = true)
    private String englishOtherIngredients;

    @Column(name = "korean_other_ingredients", nullable = true)
    private String koreanOtherIngredients;

    // 라벨 문구
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductLabelStatement> productLabelStatementList;


    public static Product of() {
        return Product.builder()
                .build();
    }

}
