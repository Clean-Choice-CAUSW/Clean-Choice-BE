package com.cleanChoice.cleanChoice.domain.intakeIngredient.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePassivityIntakeIngredientRequestDto {

    @Schema(description = "사용자가 등록하려는 성분명(성분 분석 등 서비스에서 성분 관련된 기능은 안 됨)", example = "비타민C", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fakeName;

    @Schema(description = "복용량(nullable)", example = "100.0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Double amount;

    @Schema(description = "복용량 단위(nullable)", example = "mg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String unit;

}
