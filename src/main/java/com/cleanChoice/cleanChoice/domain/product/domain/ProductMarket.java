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

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "price_unit", nullable = false)
    private String priceUnit;

    @Column(name = "price_discount", nullable = false)
    @Builder.Default
    private Long viewCount = 1L;

    @Column(name = "view_count", nullable = false)
    @Builder.Default
    private Long mismatchCount = 0L;

    @Column(name = "is_trained", nullable = false)
    @Builder.Default
    private Boolean isTrained = false;


}
