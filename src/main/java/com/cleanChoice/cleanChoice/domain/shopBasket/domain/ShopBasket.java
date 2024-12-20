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
@Table(name = "shop_basket",
        indexes = {
        @Index(name = "sb_member_idx", columnList = "member_id")
        }
)
public class ShopBasket extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "shopBasket", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<ShopBasketProductJoin> shopBasketProductJoinList;


    public static ShopBasket of(Member member, String name) {
        return ShopBasket.builder()
                .member(member)
                .name(name)
                .build();
    }

}
