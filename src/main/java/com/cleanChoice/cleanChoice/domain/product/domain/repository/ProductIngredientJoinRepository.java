package com.cleanChoice.cleanChoice.domain.product.domain.repository;

import com.cleanChoice.cleanChoice.domain.ingredient.domain.Ingredient;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductIngredientJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductIngredientJoinRepository extends JpaRepository<ProductIngredientJoin, Long> {

    List<ProductIngredientJoin> findAllByIngredient(Ingredient ingredient);
}
