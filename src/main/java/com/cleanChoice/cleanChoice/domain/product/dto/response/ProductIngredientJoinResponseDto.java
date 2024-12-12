package com.cleanChoice.cleanChoice.domain.product.dto.response;

import com.cleanChoice.cleanChoice.domain.ingredient.dto.response.IngredientResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductIngredientJoinResponseDto {

    @Schema(description = "ProductIngredientJoin id 값", example = "1")
    private Long id;

    @Schema(description = "Product id 값", example = "1")
    private Long productId;

    @Schema(description = "Ingredient DTO")
    private IngredientResponseDto ingredientResponseDto;

    @Schema(description = "1회 복용양", example = "1")
    private String servingSize;

    @Schema(description = "1회 복용양 단위", example = "Tablet(s)")
    private String servingUnit;

    @Schema(description = "복용 당 성분 섭취량", example = "100")
    private String amountPerServing;

    @Schema(description = "복용 당 성분 섭취량 단위", example = "mg")
    private String amountPerServingUnit;

    @Schema(description = "일일 권장 섭취량 대비 비율(%)", example = "5882")
    private String dailyValuePerServing;

    @Schema(description = "일일 권장 섭취량 기준 그룹 / 영어", example = "Adults and children 4 or more years of age")
    private String englishDailyValueTargetGroup;

    @Schema(description = "일일 권장 섭취량 기준 그룹 / 한글", example = "4세 이상 어린이 및 성인")
    private String koreanDailyValueTargetGroup;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
