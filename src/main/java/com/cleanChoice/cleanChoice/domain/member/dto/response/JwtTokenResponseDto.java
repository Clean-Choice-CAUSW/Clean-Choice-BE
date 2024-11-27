package com.cleanChoice.cleanChoice.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtTokenResponseDto {

    private String grantType;

    private String accessToken;

    private String refreshToken;

}
