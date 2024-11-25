package com.cleanChoice.cleanChoice.domain.embTest.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbResponseDto {

    private String productName;

    private String brandName;

    private List<Float> productNameVectorList;

    private List<Float> brandNameVectorList;

}
