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

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "image_url", nullable = true, columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "market_name", nullable = false, columnDefinition = "TEXT")
    private String url;

    @Column(name = "price", nullable = true)
    private Long price;

    @Column(name = "price_unit", nullable = true)
    private String priceUnit;

    @Column(name = "price_discount", nullable = false)
    @Builder.Default
    private Long viewCount = 0L;

    @Column(name = "view_count", nullable = false)
    @Builder.Default
    private Long mismatchCount = 0L;

    @Column(name = "is_trained", nullable = false)
    @Builder.Default
    private Boolean isTrained = false;

    public static ProductMarket of(
            Product product,
            String imageUrl,
            String url,
            Long price,
            String priceUnit
    ) {
        return ProductMarket.builder()
                .product(product)
                .imageUrl(imageUrl)
                .url(url)
                .price(price)
                .priceUnit(priceUnit)
                .build();
    }

    public void updateViewCount() {
        this.viewCount++;
    }

}
