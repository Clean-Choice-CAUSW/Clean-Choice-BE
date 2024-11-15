package com.cleanChoice.cleanChoice.domain.shopBasket.domain;

import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "shop_basket")
public class ShopBasket extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "shopBasket", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinColumn(name = "shop_basket_id")
    private List<ShopBasketProductJoin> shopBasketProductJoinList;


    public static ShopBasket of() {
        return ShopBasket.builder()
                .build();
    }

}
