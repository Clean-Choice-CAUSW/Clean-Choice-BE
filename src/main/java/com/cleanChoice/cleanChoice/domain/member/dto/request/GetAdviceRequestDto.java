package com.cleanChoice.cleanChoice.domain.member.dto.request;

import com.cleanChoice.cleanChoice.domain.member.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAdviceRequestDto {

    @Schema(description = "질문 기준이 현재 유저인지 여부(ture: 현재 로그인 유저, false: 유저 지인)")
    @NotNull(message = "질문 기준 현재 유저인지 여부는 필수 입력 값입니다.")
    private Boolean isCurrentMember;

    @Schema(description = "질문 기준이 현재 유저가 아닐 때 나이(nullable)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer age;

    @Schema(description = "질문 기준이 현재 유저가 아닐 때 성별(nullable)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Gender gender;

    @Schema(description = "질문 기준이 현재 유저가 아닐 때 임신 여부(nullable)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean isPregnant;

    @Schema(description = "질문 기준이 현재 유저가 아닐 때 현재 복용 중인 성분 이름 리스트(nullable)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<String> intakeIngredientListString;

    @Schema(description = "사용자 질문 시 선택한 productMarketId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "productMarketId는 필수입니다.")
    private Long productMarketId;

    @Schema(description = "사용자가 원하는 질문(nullable)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String question;

}
