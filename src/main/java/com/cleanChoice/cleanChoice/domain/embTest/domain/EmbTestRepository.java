package com.cleanChoice.cleanChoice.domain.embTest.domain;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmbTestRepository extends JpaRepository<EmbTest, Long> {

    @Query(value = "SELECT id, product_name, brand_name, name_vector, brand_name_vector, " +
            "(name_vector <=> CAST(:target_name_vector AS vector)) AS name_distance, " +
            "(brand_name_vector <=> CAST(:target_brand_name_vector AS vector)) AS brand_name_distance " +
            "FROM emb_test " +
            "WHERE (brand_name_vector <=> CAST(:target_brand_name_vector AS vector)) <= 0.5 " +
            "ORDER BY name_distance ASC " +
            "LIMIT 100",
            nativeQuery = true)
    List<EmbTest> findAllCos(
            @Param("target_name_vector") String target_name_vector, // 이름 정확히 일치
            @Param("target_brand_name_vector") String target_brand_name_vector // 이름 정확히 일치
    );
}
