package com.cleanChoice.cleanChoice.domain.viewRecord.domain;

import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductMarket;
import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "view_record")
public class ViewRecord extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_market_id", nullable = false)
    private ProductMarket productMarket;

    public static ViewRecord of(Member member, ProductMarket productMarket) {
        return ViewRecord.builder()
                .member(member)
                .productMarket(productMarket)
                .build();
    }

}
