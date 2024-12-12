package com.cleanChoice.cleanChoice.domain.product.domain.repository;

import com.cleanChoice.cleanChoice.domain.product.domain.NameBrandNameVector;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductMarket;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NameBrandNameVectorRepository extends JpaRepository<NameBrandNameVector, Long> {

    @Query(value = "SELECT * " +
            "FROM name_brand_name_vector " +
            "WHERE (brand_name_vector <=> CAST(:target_brand_name_vector AS vector)) <= 0.5 " +
            "ORDER BY (name_vector <=> CAST(:target_name_vector AS vector)) ASC " +
            "LIMIT 1",
            nativeQuery = true)
    Optional<NameBrandNameVector> findNameBrandNameVectorByCosDistanceTop1(
            @Param("target_name_vector") String target_name_vector,
            @Param("target_brand_name_vector") String target_brand_name_vector
    );

    @Query(value = "SELECT nbnv, " +
            "(nbnv.name_vector <=> CAST(:target_name_vector AS vector)) AS name_distance, " +
            "(nbnv.brand_name_vector <=> CAST(:target_brand_name_vector AS vector)) AS brand_name_distance " +
            "FROM name_brand_name_vector nbnv " +
            "WHERE (nbnv.brand_name_vector <=> CAST(:target_brand_name_vector AS vector)) <= 0.9 " +
            //"ORDER BY (nbnv.name_vector <=> CAST(:target_name_vector AS vector)) ASC " +
            "ORDER BY name_distance ASC " +
            "LIMIT 1",
            nativeQuery = true)
    Object [] findNameBrandNameVectorByCosineDistanceTop1WithDistance(
            @Param("target_name_vector") String target_name_vector,
            @Param("target_brand_name_vector") String target_brand_name_vector
    );

    Optional<NameBrandNameVector> findByProductMarket(ProductMarket productMarket);
}
