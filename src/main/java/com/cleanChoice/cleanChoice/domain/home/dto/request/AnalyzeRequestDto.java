package com.cleanChoice.cleanChoice.domain.home.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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

    @Schema(description = "상품 이름", example = "테스트 상품", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "productName 은 필수 입력 값입니다.")
    private String productName;

    @Schema(description = "브랜드 이름", example = "테스트 브랜드", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "brandName 은 필수 입력 값입니다.")
    private String brandName;

    @Schema(description = "상품 이미지 url", example = "https://www.amazon.com/test.jpg", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "imageUrl 은 필수 입력 값입니다.")
    private String imageUrl;

    @Schema(description = "상품 가격(수) nullable 임", example = "10000", requiredMode = Schema.RequiredMode.REQUIRED)
    @Positive(message = "price 는 0보다 커야 합니다.")
    private Long price;

    @Schema(description = "상품 가격 단위 nullable 임", example = "USD", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "priceUnit 은 필수 입력 값입니다.")
    private String priceUnit;

}
