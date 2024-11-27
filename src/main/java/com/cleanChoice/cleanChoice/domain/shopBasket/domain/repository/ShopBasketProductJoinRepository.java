package com.cleanChoice.cleanChoice.domain.shopBasket.domain.repository;

import com.cleanChoice.cleanChoice.domain.shopBasket.domain.ShopBasketProductJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopBasketProductJoinRepository extends JpaRepository<ShopBasketProductJoin, Long> {
}
