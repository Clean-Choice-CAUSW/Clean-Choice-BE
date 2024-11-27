package com.cleanChoice.cleanChoice.domain.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    @Schema(description = "Product id 값")
    private Long id;

    @Schema(description = "DSLD id 값")
    private Long dsldId;

    @Schema(description = "DSLD 등록 페이지 url")
    private String dsldUrl;

    @Schema(description = "Product 이름")
    private String name;

    @Schema(description = "Product에 포함된 Ingredient 관계의 목록 Dto List")
    private List<ProductIngredientJoinResponseDto> productIngredientJoinResponseDtoList;

    @Schema(description = "제조사명 (ex: Vitamin World)")
    private String brandName;

    @Schema(description = "제조 국가")
    private String madeInCountry;

    @Schema(description = "제품 총량 / 영어", example = "100 Easy To Swallow Coated Tablet(s))")
    private String englishNetContent;

    @Schema(description = "제품 총량 / 한글", example = "100 정")
    private String koreanNetContent;

    @Schema(description = "1회 제공량 (ex: 1 tsp [3 g-9 g])")
    private String servingSize;

    @Schema(description = "제품 종류(LangauL) / 영어", example = "Non-Nutrient/Non-Botanical [A1309]")
    private String englishProductType;

    @Schema(description = "제품 종류(LangauL) / 한글", example = "비영양성/비식물성 [A1309]")
    private String koreanProductType;

    @Schema(description = "섭취 형태(LanguaL) / 영어", example = "Tablet or Pill [E0155])")
    private String englishSupplementForm;

    @Schema(description = "섭취 형태(LanguaL) / 한글", example = "정 [E0155]")
    private String koreanSupplementForm;

    @Schema(description = "권장 복용법 / 영어", example = "DIRECTIONS: For adults, take one (1) to two (2) tablets daily, preferably with a meal.")
    private String englishSuggestedUse;

    @Schema(description = "권장 복용법 / 한글", example = "성인은 하루에 한 (1) 또는 두 (2) 정을 섭취하십시오. 식사와 함께 섭취하는 것이 좋습니다.")
    private String koreanSuggestedUse;

    @Schema(description = "기타 성분 / 영어", example = "Dicalcium Phosphate, Vegetable Cellulose, Croscarmellose, Vegetable Magnesium Stearate, Silica, Vegetable Cellulose Coating")
    private String englishOtherIngredients;

    @Schema(description = "기타 성분 / 한글", example = "이산화칼슘, 식물 섬유소, 크로스카르멜로스, 식물 마그네슘 스테아레이트, 실리카, 식물 섬유소 코팅")
    private String koreanOtherIngredients;

    @Schema(description = "Product에 포함된 Label Statement 관계의 목록 Dto List")
    private List<ProductLabelStatementResponseDto> productLabelStatementResponseDtoList;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
