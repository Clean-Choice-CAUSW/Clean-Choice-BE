package com.cleanChoice.cleanChoice.domain.product.dto.response;

import com.cleanChoice.cleanChoice.domain.product.domain.StatementType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductLabelStatementResponseDto {

    @Schema(description = "ProductLabelStatement id 값", example = "1")
    private Long id;

    @Schema(description = "Product id 값", example = "1")
    private Long productId;

    @Schema(description = "라벨 타입",
            example = "BRANDING_STATEMENT(\"브랜딩 문구\"),\n" +
                    "    STATEMENT_OF_IDENTITY(\"식품의 명칭\"),\n" +
                    "    FORMULATION(\"성분\"),\n" +
                    "    PRECAUTIONS(\"주의사항\"),\n" +
                    "    PRODUCT_OR_VERSION_CODE(\"제품/버전 코드\"),\n" +
                    "    SEAL_SYMBOLS(\"인증/로고\"),\n" +
                    "    SUGGESTED_USE(\"사용방법\"),\n" +
                    "    OTHER(\"기타\")")
    private StatementType statementType;

    @Schema(description = "라벨 타입 상세 한글 문자열 값", example = "주의사항")
    private String statementTypeStringValue;

    @Schema(description = "라벨 내용 / 영어", example = "WARNING: If you are pregnant, nursing or taking any medications, consult your doctor before use. Discontinue use and consult your doctor if any adverse reactions occur. Keep out of reach of children. Store in a cool, dry place. Do not use if seal under cap is broken or missing.")
    private String englishStatement;

    @Schema(description = "라벨 내용 / 한글", example = "경고: 임신 중이거나 수유 중이거나 약을 복용 중이라면 사용하기 전에 의사와 상의하십시오. 부작용이 발생하면 사용을 중단하고 의사와 상의하십시오. 어린이의 손이 닿지 않는 곳에 보관하십시오. 서늘하고 건조한 곳에 보관하십시오. 뚜껑 아래의 봉인이 깨지거나 없는 경우 사용하지 마십시오.")
    private String koreanStatement;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
