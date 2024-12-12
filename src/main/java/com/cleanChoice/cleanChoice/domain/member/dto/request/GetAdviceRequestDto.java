package com.cleanChoice.cleanChoice.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAdviceRequestDto {

    @Schema(description = "사용자 질문 시 선택한 productMarketId")
    @NotNull(message = "productMarketId는 필수입니다.")
    private Long productMarketId;

    @Schema(description = "사용자가 원하는 질문(nullable)")
    private String question;

}
