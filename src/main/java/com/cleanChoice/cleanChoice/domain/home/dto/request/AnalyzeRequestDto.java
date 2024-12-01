package com.cleanChoice.cleanChoice.domain.home.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyzeRequestDto {

    @Schema(description = "마켓 url", example = "https://www.amazon.com")
    @NotBlank(message = "url은 필수 입력 값입니다.")
    private String url;

    @Schema(description = "상품 이름", example = "테스트 상품")
    @NotBlank(message = "productName 은 필수 입력 값입니다.")
    private String productName;

    @Schema(description = "브랜드 이름", example = "테스트 브랜드")
    @NotBlank(message = "brandName 은 필수 입력 값입니다.")
    private String brandName;

    /*
    @Schema(description = "상품 가격(수) nullable 임", example = "10000", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long price;

    @Schema(description = "상품 가격 단위 nullable 임", example = "USD", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String priceUnit;

     */

}
