package com.cleanChoice.cleanChoice.domain.product.domain;

import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "product_market",
indexes = {
    @Index(name = "product_idx", columnList = "product_id")
})
public class ProductMarket extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "market_name", nullable = false)
    private String url;

    @Column(name = "price", nullable = true)
    private Long price;

    @Column(name = "price_unit", nullable = true)
    private String priceUnit;


}
