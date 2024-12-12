package com.cleanChoice.cleanChoice.domain.product.domain.repository;

import com.cleanChoice.cleanChoice.domain.product.domain.Product;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMarketRepository extends JpaRepository<ProductMarket, Long> {

    List<ProductMarket> findAllByUrl(String url);

    List<ProductMarket> findAllByProduct(Product product);

}
