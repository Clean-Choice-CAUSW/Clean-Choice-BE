package com.cleanChoice.cleanChoice.domain.embTest.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbAnalyzeResponseDto {

    private String productName;

    private String brandName;

    private List<Float> productNameVectorList;

    private List<Float> brandNameVectorList;

    private Double nameDistance;

    private Double brandNameDistance;

}
