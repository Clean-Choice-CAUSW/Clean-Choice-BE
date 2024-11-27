package com.cleanChoice.cleanChoice.domain.ingredient.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BanType {

    PREGNANT_BANDED("임부 금기"),
    OLD_CAUTION("노인 주의");

    private final String description;

}
