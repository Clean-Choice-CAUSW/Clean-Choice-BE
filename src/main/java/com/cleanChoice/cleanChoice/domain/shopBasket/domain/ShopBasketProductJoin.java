package com.cleanChoice.cleanChoice.domain.shopBasket.domain;

import com.cleanChoice.cleanChoice.domain.product.domain.Product;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductMarket;
import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "shop_basket_product",
        indexes = {
        @Index(name = "sbp_shop_basket_idx", columnList = "shop_basket_id")
        }
)
public class ShopBasketProductJoin extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_basket_id", nullable = false)
    private ShopBasket shopBasket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_market_id", nullable = false)
    private ProductMarket productMarket;

    public static ShopBasketProductJoin of(
            ShopBasket shopBasket,
            ProductMarket productMarket
    ) {
        return ShopBasketProductJoin.builder()
                .shopBasket(shopBasket)
                .productMarket(productMarket)
                .build();
    }
}
