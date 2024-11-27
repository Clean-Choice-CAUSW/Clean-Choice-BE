package com.cleanChoice.cleanChoice.domain.ingredient.domain.repository;

import com.cleanChoice.cleanChoice.domain.ingredient.domain.CombineUseBanedIngredientInfo;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.Ingredient;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CombineUseBanedIngredientRepository extends JpaRepository<CombineUseBanedIngredientInfo, Long> {

    @Query(value = "SELECT * " +
            "FROM combine_use_baned_ingredient_info " +
            "WHERE ingredient_id = :ingredient OR combine_ingredient_id = :ingredient",
            nativeQuery = true)
    List<CombineUseBanedIngredientInfo> findAllByIngredientAndCombineIngredient(@Param("ingredient") Ingredient ingredient);

}
