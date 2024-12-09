package com.cleanChoice.cleanChoice.domain.ingredient.domain.repository;

import com.cleanChoice.cleanChoice.domain.ingredient.domain.Ingredient;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query(value = "SELECT *" +
            "FROM ingredient " +
            "WHERE name = :name OR korean_name = :name",
            nativeQuery = true
    )
    Optional<Ingredient> findByName(
            @Param("name") String name
    );


    Optional<Ingredient> findByEnglishName(String englishName);

    Optional<Ingredient> findByKoreanName(String koreanName);
}
