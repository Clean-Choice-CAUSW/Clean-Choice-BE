package com.cleanChoice.cleanChoice.domain.product.domain;

import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "name_brand_name_vector",
indexes = {
        @Index(name = "nbnv_product_idx", columnList = "product_id"),
        @Index(name = "nbnv_brand_idx", columnList = "brand_id"),
        @Index(name = "nbnv_nameVector_idx", columnList = "name_vector"),
        @Index(name = "nbnv_brandNameVector_idx", columnList = "brand_name_vector")
})
public class NameBrandNameVector extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = true)
    private ProductMarket productMarket;

    @Column(name = "name_vector", nullable = false)
    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 384) // MiniLM-L6-v2 dimensions
    private float[] nameVector;

    @Column(name = "brand_name_vector", nullable = false)
    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 384) // MiniLM-L6-v2 dimensions
    private float[] brandNameVector;

    public static NameBrandNameVector createWithProduct(
            Product product,
            List<Float> nameVector,
            List<Float> brandNameVector
    ) {
        return NameBrandNameVector.builder()
                .product(product)
                .nameVector(convertListToFloatArray(nameVector))
                .brandNameVector(convertListToFloatArray(brandNameVector))
                .build();
    }

    public static NameBrandNameVector createWithProductMarket(
            ProductMarket productMarket,
            List<Float> nameVector,
            List<Float> brandNameVector
    ) {
        return NameBrandNameVector.builder()
                .productMarket(productMarket)
                .nameVector(convertListToFloatArray(nameVector))
                .brandNameVector(convertListToFloatArray(brandNameVector))
                .build();
    }

    private static float[] convertListToFloatArray(List<Float> list) {
        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++)
            array[i] = list.get(i);
        return array;
    }

}
