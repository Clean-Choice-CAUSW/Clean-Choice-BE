package com.cleanChoice.cleanChoice.global.util;

public class StaticValue {
    public final static Long JWT_ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 60; // 1시간
    public final static Long JWT_REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 14; // 2주
    public static final Long MAX_VIEW_RECORD_SIZE = 10L;
}
