package com.cleanChoice.cleanChoice.domain.ingredient.domain.repository;

import com.cleanChoice.cleanChoice.domain.ingredient.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
