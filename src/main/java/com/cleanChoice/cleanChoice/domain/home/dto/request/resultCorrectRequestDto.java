package com.cleanChoice.cleanChoice.domain.home.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class resultCorrectRequestDto {

    private String url;

    private String name;

    private String brandName;

    private Long price;

    private String priceUnit;

}
