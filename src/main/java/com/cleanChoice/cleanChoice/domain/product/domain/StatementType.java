package com.cleanChoice.cleanChoice.domain.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatementType {

    BRANDING_STATEMENT("브랜딩 문구"),
    STATEMENT_OF_IDENTITY("식품의 명칭"),
    FORMULATION("성분"),
    PRECAUTIONS("주의사항"),
    PRODUCT_OR_VERSION_CODE("제품/버전 코드"),
    SEAL_SYMBOLS("인증/로고"),
    SUGGESTED_USE("사용방법"),
    OTHER("기타");

    private final String value;

    public static StatementType of(String value) {
        for (StatementType statementType : StatementType.values()) {
            if (statementType.getValue().equals(value)) {
                return statementType;
            }
        }
        return OTHER;
    }
}
