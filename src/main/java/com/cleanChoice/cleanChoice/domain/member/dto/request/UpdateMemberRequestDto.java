package com.cleanChoice.cleanChoice.domain.member.dto.request;

import com.cleanChoice.cleanChoice.domain.member.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberRequestDto {

    @Schema(description = "로그인 아이디(미변경시 null 가능)", example = "testPassword123", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String password;

    @Schema(description = "이름(미변경시 null 가능)", example = "홍길동", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String name;

    @Schema(description = "나이(미변경시 null 가능)", example = "20", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer age;

    @Schema(description = "성별(MALE / FEMALE) (미변경시 null 가능)", example = "MALE", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Gender gender;

    @Schema(description = "임신 여부(미변경시 null 가능)", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean isPregnant;

}
