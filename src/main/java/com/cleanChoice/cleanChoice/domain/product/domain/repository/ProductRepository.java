package com.cleanChoice.cleanChoice.domain.product.domain.repository;

import com.cleanChoice.cleanChoice.domain.product.domain.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * " +
            "FROM product " +
            "WHERE (brand_name_vector <=> CAST(:target_brand_name_vector AS vector)) <= 0.5 " +
            "ORDER BY (name_vector <=> CAST(:target_name_vector AS vector)) ASC " +
            "LIMIT 1",
            nativeQuery = true)
    Product findProductByCosDistanceTop1(
           @Param("target_name_vector") String target_name_vector,
           @Param("target_brand_name_vector") String target_brand_name_vector
    );

}
