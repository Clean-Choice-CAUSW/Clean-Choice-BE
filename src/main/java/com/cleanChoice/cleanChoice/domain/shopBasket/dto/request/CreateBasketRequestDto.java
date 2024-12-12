package com.cleanChoice.cleanChoice.domain.shopBasket.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBasketRequestDto {

    @Schema(description = "장바구니 이름", example = "장바구니 이름 예시", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "장바구니 이름은 필수입니다.")
    private String basketName;

}
