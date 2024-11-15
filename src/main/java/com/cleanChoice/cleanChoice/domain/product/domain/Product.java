package com.cleanChoice.cleanChoice.domain.product.domain;

import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "product")
public class Product extends BaseEntity {

    @Setter(value = AccessLevel.PUBLIC)
    @Column(name = "englishName", nullable = true)
    private String englishName;

    @Setter(value = AccessLevel.PUBLIC)
    @Column(name = "koreanName", nullable = true)
    private String koreanName;

    @Column(name = "serving_size", nullable = true)
    private Double servingSize;

    @Column(name = "serving_unit", nullable = true)
    private String servingUnit;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductIngredientJoin> productIngredientJoinList;

    private String brandName;



    public static Product of() {
        return Product.builder()
                .build();
    }

}
