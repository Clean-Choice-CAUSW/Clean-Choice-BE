package com.cleanChoice.cleanChoice.domain.embedding.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbRequestDto {

    private String productName;

    private String brandName;

}
