package com.cleanChoice.cleanChoice.domain.home.openAi.dto.convert;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductMarketLLMResponseDto {

    // Product

    @Schema(description = "제품 영어명", example = "B-2 100mg")
    private String name;

    @Schema(description = "제품 성분 정보 리스트")
    private List<ProductIngredientJoinLLMResponseDto> productIngredientJoinLLMResponseDtoList;

    @Schema(description = "제품 제조사명", example = "Vitamin World")
    private String brandName;

    @Schema(description = "제조국", example = "USA")
    private String madeInCountry;

    @Schema(description = "제품 총량(영어)", example = "100 Easy to Swallow Coated Tablet(s)")
    private String englishNetContent;

    @Schema(description = "제품 총량(한글)", example = "100정")
    private String koreanNetContent;

    @Schema(description = "1회 제공량", example = "1 tsp [3 g-9 g]")
    private String servingSize;

    @Schema(description = "제품 종류 (영어)", example = "Non-Nutrient/Non-Botanical")
    private String englishProductType;

    @Schema(description = "제품 종류 (한글)", example = "비영양/비식물성")
    private String koreanProductType;

    @Schema(description = "섭취 형태 (영어)", example = "Tablet or Pill")
    private String englishSupplementForm;

    @Schema(description = "섭취 형태 (한글)", example = "정제 알약")
    private String koreanSupplementForm;

    @Schema(description = "권장 복용법(영어)", example = "For adults, take one (1) tablet daily, preferably with a meal.")
    private String englishSuggestedUse;

    @Schema(description = "권장 복용법(한글)", example = "성인은 하루 한 정을 식사와 함께 섭취하십시오.")
    private String koreanSuggestedUse;

    @Schema(description = "기타 성분(영어)", example = "Dicalcium Phosphate, Vegetable Cellulose, Vegetable Stearic Acid, Silica, Vegetable Magnesium Stearate.")
    private String englishOtherIngredients;

    @Schema(description = "기타 성분(한글)", example = "이산화칼슘, 식물 섬유소, 식물 스테아릭산, 실리카, 식물 마그네슘 스테아레이트.")
    private String koreanOtherIngredients;

    @Schema(description = "라벨 문구 리스트")
    private List<ProductLabelStatementLLMResponseDto> productLabelStatementLLMResponseDtoList;

    // ProductMarket

    @Schema(description = "상품 메인 이미지 url")
    private String imageUrl;

    @Schema(description = "상품 가격", example = "10000")
    private Long price;

    @Schema(description = "상품 가격 단위", example = "USD")
    private String priceUnit;

}
