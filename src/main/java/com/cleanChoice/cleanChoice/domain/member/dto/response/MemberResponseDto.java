package com.cleanChoice.cleanChoice.domain.member.dto.response;

import com.cleanChoice.cleanChoice.domain.member.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

    @Schema(description = "회원 식별자", example = "1")
    private Long id;

    @Schema(description = "아이디", example = "testId123")
    private String loginId;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "나이", example = "20")
    private Integer age;

    @Schema(description = "성별", example = "MAEL / FEMALE")
    private Gender gender;

    @Schema(description = "임신 여부", example = "true / false")
    private Boolean isPregnant;

}
