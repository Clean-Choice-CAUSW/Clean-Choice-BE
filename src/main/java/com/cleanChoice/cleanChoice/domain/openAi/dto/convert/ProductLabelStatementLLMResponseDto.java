package com.cleanChoice.cleanChoice.domain.openAi.dto.convert;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductLabelStatementLLMResponseDto {

    @Schema(description = "제품 라벨 문구(영어)", example = "WARNING: If you are pregnant, nursing or taking any medications, consult your doctor before use. Discontinue use and consult your doctor if any adverse reactions occur. Keep out of reach of children. Store in a cool, dry place. Do not use if seal under cap is broken or missing.")
    private String englishLabelStatement;

    @Schema(description = "제품 라벨 문구(한글)", example = "주의: 임신 중이거나 수유 중이거나 약을 복용 중인 경우 사용하기 전에 의사와 상의하십시오. 부작용이 발생하면 사용을 중단하고 의사와 상의하십시오. 어린이의 손이 닿지 않는 곳에 보관하십시오. 서늘하고 건조한 곳에 보관하십시오. 뚜껑 아래의 봉인이 깨지거나 없는 경우 사용하지 마십시오.")
    private String koreanLabelStatement;

}
