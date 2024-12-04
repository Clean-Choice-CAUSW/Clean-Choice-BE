package com.cleanChoice.cleanChoice.domain.product.domain;

import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.product.dto.request.PersonalizedInfoRequestDto;
import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "personalized_info")
public class PersonalizedInfo extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_market_id", nullable = false)
    private ProductMarket productMarket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "price", nullable = true)
    private Long price;

    @Column(name = "price_unit", nullable = true)
    private String priceUnit;


    // Product

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "brand_name", nullable = true)
    private String brandName;

    @Column(name = "made_in_country", nullable = true)
    private String madeInCountry;

    @Column(name = "english_net_content", nullable = true)
    private String englishNetContent;

    @Column(name = "korean_net_content", nullable = true)
    private String koreanNetContent;

    @Column(name = "serving_size", nullable = true)
    private String servingSize;

    @Column(name = "english_product_type", nullable = true)
    private String englishProductType;

    @Column(name = "korean_product_type", nullable = true)
    private String koreanProductType;

    @Column(name = "english_supplement_form", nullable = true)
    private String englishSupplementForm;

    @Column(name = "korean_supplement_form", nullable = true)
    private String koreanSupplementForm;

    @Column(name = "english_suggested_use", nullable = true)
    private String englishSuggestedUse;

    @Column(name = "korean_suggested_use", nullable = true)
    private String koreanSuggestedUse;

    @Column(name = "english_other_ingredients", nullable = true)
    private String englishOtherIngredients;

    @Column(name = "korean_other_ingredients", nullable = true)
    private String koreanOtherIngredients;

    public static PersonalizedInfo from(
            ProductMarket productMarket,
            Member member,
            PersonalizedInfoRequestDto personalizedInfoRequestDto
    ) {
        return PersonalizedInfo.builder()
                .productMarket(productMarket)
                .member(member)
                .price(personalizedInfoRequestDto.getPrice() == null ?
                        productMarket.getPrice() :
                        personalizedInfoRequestDto.getPrice())
                .priceUnit(personalizedInfoRequestDto.getPriceUnit() == null ?
                        productMarket.getPriceUnit() :
                        personalizedInfoRequestDto.getPriceUnit())
                .name(personalizedInfoRequestDto.getName() == null ?
                        productMarket.getProduct().getName() :
                        personalizedInfoRequestDto.getName())
                .brandName(personalizedInfoRequestDto.getBrandName() == null ?
                        productMarket.getProduct().getBrandName() :
                        personalizedInfoRequestDto.getBrandName())
                .madeInCountry(personalizedInfoRequestDto.getMadeInCountry() == null ?
                        productMarket.getProduct().getMadeInCountry() :
                        personalizedInfoRequestDto.getMadeInCountry())
                .englishNetContent(personalizedInfoRequestDto.getEnglishNetContent() == null ?
                        productMarket.getProduct().getEnglishNetContent() :
                        personalizedInfoRequestDto.getEnglishNetContent())
                .koreanNetContent(personalizedInfoRequestDto.getKoreanNetContent() == null ?
                        productMarket.getProduct().getKoreanNetContent() :
                        personalizedInfoRequestDto.getKoreanNetContent())
                .servingSize(personalizedInfoRequestDto.getServingSize() == null ?
                        productMarket.getProduct().getServingSize() :
                        personalizedInfoRequestDto.getServingSize())
                .englishProductType(personalizedInfoRequestDto.getEnglishProductType() == null ?
                        productMarket.getProduct().getEnglishProductType() :
                        personalizedInfoRequestDto.getEnglishProductType())
                .koreanProductType(personalizedInfoRequestDto.getKoreanProductType() == null ?
                        productMarket.getProduct().getKoreanProductType() :
                        personalizedInfoRequestDto.getKoreanProductType())
                .englishSupplementForm(personalizedInfoRequestDto.getEnglishSupplementForm() == null ?
                        productMarket.getProduct().getEnglishSupplementForm() :
                        personalizedInfoRequestDto.getEnglishSupplementForm())
                .koreanSupplementForm(personalizedInfoRequestDto.getKoreanSupplementForm() == null ?
                        productMarket.getProduct().getKoreanSupplementForm() :
                        personalizedInfoRequestDto.getKoreanSupplementForm())
                .englishSuggestedUse(personalizedInfoRequestDto.getEnglishSuggestedUse() == null ?
                        productMarket.getProduct().getEnglishSuggestedUse() :
                        personalizedInfoRequestDto.getEnglishSuggestedUse())
                .koreanSuggestedUse(personalizedInfoRequestDto.getKoreanSuggestedUse() == null ?
                        productMarket.getProduct().getKoreanSuggestedUse() :
                        personalizedInfoRequestDto.getKoreanSuggestedUse())
                .englishOtherIngredients(personalizedInfoRequestDto.getEnglishOtherIngredients() == null ?
                        productMarket.getProduct().getEnglishOtherIngredients() :
                        personalizedInfoRequestDto.getEnglishOtherIngredients())
                .koreanOtherIngredients(personalizedInfoRequestDto.getKoreanOtherIngredients() == null ?
                        productMarket.getProduct().getKoreanOtherIngredients() :
                        personalizedInfoRequestDto.getKoreanOtherIngredients())
                .build();
    }

}
