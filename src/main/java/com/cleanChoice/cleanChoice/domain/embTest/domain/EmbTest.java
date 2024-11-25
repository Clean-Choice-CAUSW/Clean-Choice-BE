package com.cleanChoice.cleanChoice.domain.embTest.domain;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "emb_test")
public class EmbTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "name_vector")
    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 384) // MiniLM-L6-v2 dimensions
    private float[] nameVector;

    @Column(name = "brand_name_vector")
    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 384) // MiniLM-L6-v2 dimensions
    private float[] brandNameVector;

    @Column(name = "name_distance")
    private Double nameDistance;

    @Column(name = "brand_name_distance")
    private Double brandNameDistance;

}
