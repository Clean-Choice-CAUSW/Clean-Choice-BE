package com.cleanChoice.cleanChoice.domain.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnalyzeType {

    LLM_ANALYZE("LLM 파싱 결과"),
    DB_ANALYZE("DB Cosine Distance 측정 or DB에 일치하는 URL 존재"),
    DB_MAKE("DB에 새로운 URL 추가");

    private final String description;

}
