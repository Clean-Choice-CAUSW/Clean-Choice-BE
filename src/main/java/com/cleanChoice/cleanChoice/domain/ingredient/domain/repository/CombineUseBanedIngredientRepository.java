package com.cleanChoice.cleanChoice.domain.ingredient.domain.repository;

import com.cleanChoice.cleanChoice.domain.ingredient.domain.CombineUseBanedIngredientInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CombineUseBanedIngredientRepository extends JpaRepository<CombineUseBanedIngredientInfo, Long> {
}
