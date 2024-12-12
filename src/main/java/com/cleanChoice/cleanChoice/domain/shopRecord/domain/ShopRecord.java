package com.cleanChoice.cleanChoice.domain.shopRecord.domain;

import com.cleanChoice.cleanChoice.domain.member.domain.Member;
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
@Table(name = "shop_record",
        indexes = {
        @Index(name = "sr_member_idx", columnList = "member_id")
        }
)
public class ShopRecord extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_market_id", nullable = false)
    private ProductMarket productMarket;

    public static ShopRecord of(
            Member member,
            ProductMarket productMarket
    ) {
        return ShopRecord.builder()
                .member(member)
                .productMarket(productMarket)
                .build();
    }

}
