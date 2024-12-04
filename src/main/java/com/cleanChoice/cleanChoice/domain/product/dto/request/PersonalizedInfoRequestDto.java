package com.cleanChoice.cleanChoice.domain.product.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalizedInfoRequestDto {

    @Schema(description = "변경하고자 하는 가격 값(null 입력 시 원래 값 적용)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "10000")
    private Long price;

    @Schema(description = "변경하고자 하는 가격 단위(null 입력 시 원래 값 적용)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "KRW")
    private String priceUnit;

    @Schema(description = "변경하고자 하는 상품명(null 입력 시 원래 값 적용)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "product")
    private String name;

    @Schema(description = "변경하고자 하는 브랜드명(null 입력 시 원래 값 적용)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "brand")
    private String brandName;

    @Schema(description = "변경하고자 하는 상품 설명(영어)(null 입력 시 원래 값 적용)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "product description")
    private String madeInCountry;

    @Schema(description = "변경하고자 하는 상품 설명(한글)(null 입력 시 원래 값 적용)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "상품 설명")
    private String englishNetContent;

    @Schema(description = "변경하고자 하는 상품 설명(한글)(null 입력 시 원래 값 적용)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "상품 설명")
    private String koreanNetContent;

    @Schema(description = "변경하고자 하는 1회 제공량(null 입력 시 원래 값 적용)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "알약 1개")
    private String servingSize;

    @Schema(description = "변경하고자 하는 상품 타입(영어)(null 입력 시 원래 값 적용)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Non-Nutrient/Non-Botanical")
    private String englishProductType;

    @Schema(description = "변경하고자 하는 상품 타입(한글)(null 입력 시 원래 값 적용)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "비영양/비식물성")
    private String koreanProductType;

    @Schema(description = "변경하고자 하는 섭취 형태(영어)(null 입력 시 원래 값 적용)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Tablet or Pill")
    private String englishSupplementForm;

    @Schema(description = "변경하고자 하는 섭취 형태(한글)(null 입력 시 원래 값 적용)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "정제 알약")
    private String koreanSupplementForm;

    @Schema(description = "변경하고자 하는 권장 복용법(영어)(null 입력 시 원래 값 적용)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "For adults, take one (1) tablet daily, preferably with a meal.")
    private String englishSuggestedUse;

    @Schema(description = "변경하고자 하는 권장 복용법(한글)(null 입력 시 원래 값 적용)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "성인은 하루 한 정을 식사와 함께 섭취하십시오.")
    private String koreanSuggestedUse;

    @Schema(description = "변경하고자 하는 기타 성분(영어)(null 입력 시 원래 값 적용)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Dicalcium Phosphate, Vegetable Cellulose, Vegetable Stearic Acid, Silica, Vegetable Magnesium Stearate.")
    private String englishOtherIngredients;

    @Schema(description = "변경하고자 하는 기타 성분(한글)(null 입력 시 원래 값 적용)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "이산화칼슘, 식물 섬유소, 식물 스테아릭산, 실리카, 식물 마그네슘 스테아레이트.")
    private String koreanOtherIngredients;

}
