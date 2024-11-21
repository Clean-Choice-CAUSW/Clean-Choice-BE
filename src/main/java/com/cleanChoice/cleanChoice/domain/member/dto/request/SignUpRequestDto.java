package com.cleanChoice.cleanChoice.domain.member.dto.request;

import com.cleanChoice.cleanChoice.domain.member.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {

    @Schema(description = "아이디(5~20자)", example = "testId123", requiredMode = Schema.RequiredMode.REQUIRED)
    @Length(min = 5, max = 20, message = "아이디는 5자 이상 20자 이하여야 합니다.")
    private String loginId;

    @Schema(description = "비밀번호(8~20자)", example = "testPassword123", requiredMode = Schema.RequiredMode.REQUIRED)
    @Length(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    private String password;

    @Schema(description = "이름", example = "홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @Schema(description = "나이", example = "20", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "나이는 필수 입력 값입니다.")
    private Integer age;

    @Schema(description = "성별(MALE / FEMALE)", example = "MALE", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "성별은 필수 입력 값입니다.")
    private Gender gender;

    @Schema(description = "임신 여부", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @NotNull(message = "임신 여부는 필수 입력 값입니다.")
    private Boolean isPregnant;

}
