package com.cleanChoice.cleanChoice.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignoutRequestDto {

    @NotBlank
    @Schema(description = "액세스 토큰", requiredMode = Schema.RequiredMode.REQUIRED)
    private String accessToken;

    @NotBlank
    @Schema(description = "리프레시 토큰", requiredMode = Schema.RequiredMode.REQUIRED)
    private String refreshToken;

}
