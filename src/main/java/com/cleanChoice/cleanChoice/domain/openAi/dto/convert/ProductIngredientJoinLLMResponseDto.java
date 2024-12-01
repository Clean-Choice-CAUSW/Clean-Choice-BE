package com.cleanChoice.cleanChoice.domain.openAi.dto.convert;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductIngredientJoinLLMResponseDto {

    // Ingredient

    @Schema(description = "성분 카테고리(영어)", example = "Vitamins")
    private String englishCategory;

    @Schema(description = "성분 카테고리(한글)", example = "비타민")
    private String koreanCategory;

    @Schema(description = "성분명(영어)", example = "Vitamin C")
    private String englishName;

    @Schema(description = "성분명(한글)", example = "비타민 C")
    private String koreanName;

    @Schema(description = "효능", example = "비타민 C는 강력한 항산화제로 알려져 있으며, 피부 건강과 면역 체계를 지원합니다.")
    private String effectiveness;

    @Schema(description = "해당 성분 주의사항", example = "비타민 C는 과다 복용 시 설사, 구토, 복통, 두통, 구역, 피로감, 구역 증상이 나타날 수 있습니다.")
    private String caution;

    // ProductIngredientJoin

    @Schema(description = "1회 복용양", example = "1.0")
    private Double servingSize;

    @Schema(description = "1회 복용양 단위", example = "Tablet(s)")
    private String servingUnit;

    @Schema(description = "복용 당 성분 섭취량", example = "100.0")
    private Double amountPerServing;

    @Schema(description = "복용 당 성분 섭취량 단위", example = "mg")
    private String amountPerServingUnit;

    @Schema(description = "일일 권장 섭취량 대비 비율(%)", example = "5882.0")
    private Double dailyValuePerServing;

    @Schema(description = "일일 권장 섭취량 기준 그룹", example = "Adults and children 4 or more years of age")
    private String englishDailyValueTargetGroup;

    @Schema(description = "일일 권장 섭취량 기준 그룹", example = "4세 이상 어린이 및 성인")
    private String koreanDailyValueTargetGroup;

}
