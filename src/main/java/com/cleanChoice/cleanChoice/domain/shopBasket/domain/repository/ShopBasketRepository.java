package com.cleanChoice.cleanChoice.domain.shopBasket.domain.repository;

import com.cleanChoice.cleanChoice.domain.shopBasket.domain.ShopBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopBasketRepository extends JpaRepository<ShopBasket, Long> {

}
