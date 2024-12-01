package com.cleanChoice.cleanChoice.domain.product.domain.repository.dto;

import com.cleanChoice.cleanChoice.domain.product.domain.NameBrandNameVector;
import lombok.*;

@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NameVectorWithDistanceDto {

    private NameBrandNameVector nameBrandNameVector;

    private Double nameDistance;

    private Double brandNameDistance;

    public static NameVectorWithDistanceDto of(NameBrandNameVector nameBrandNameVector, Double nameDistance, Double brandNameDistance) {
        return NameVectorWithDistanceDto.builder()
                .nameBrandNameVector(nameBrandNameVector)
                .nameDistance(nameDistance)
                .brandNameDistance(brandNameDistance)
                .build();
    }

}
