package com.cleanChoice.cleanChoice.domain.product.domain;

import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "name_brand_name_vector")
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

}
