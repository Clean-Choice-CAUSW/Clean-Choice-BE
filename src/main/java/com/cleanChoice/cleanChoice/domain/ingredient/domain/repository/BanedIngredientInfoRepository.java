package com.cleanChoice.cleanChoice.domain.ingredient.domain.repository;

import com.cleanChoice.cleanChoice.domain.ingredient.domain.BanedIngredientInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanedIngredientInfoRepository extends JpaRepository<BanedIngredientInfo, Long> {

}
