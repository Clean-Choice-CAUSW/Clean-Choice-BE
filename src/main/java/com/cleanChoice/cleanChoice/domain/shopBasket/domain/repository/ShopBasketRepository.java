package com.cleanChoice.cleanChoice.domain.shopBasket.domain.repository;

import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.shopBasket.domain.ShopBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopBasketRepository extends JpaRepository<ShopBasket, Long> {

    List<ShopBasket> findAllByMember(Member member);

}
