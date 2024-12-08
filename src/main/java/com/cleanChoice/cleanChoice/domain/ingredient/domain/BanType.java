package com.cleanChoice.cleanChoice.domain.ingredient.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BanType {


    OLD_CAUTION("노인 주의"),
    AGE_BANED("연령 금기"),
    VOLUME_CAUTION("복용량 주의"),
    PREGNANT_BANED("임부 금기"),
    PERIOD_CAUTION("투여 기간 주의"),
    DUPLICATE_EFFICACY("복용 효능 중복 주의"),

    OTHER("기타");

    private final String description;

    public static BanType of(String description) {
        for (BanType banType : BanType.values()) {
            if (banType.getDescription().equals(description)) {
                return banType;
            }
        }
        return OTHER;
    }

}
