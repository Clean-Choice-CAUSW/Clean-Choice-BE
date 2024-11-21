package com.cleanChoice.cleanChoice.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequestDto {

    @NotBlank(message = "로그인 아이디는 필수 입력 값입니다.")
    @Schema(description = "로그인 아이디", example = "testId123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String loginId;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Schema(description = "비밀번호", example = "testPassword123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

}
