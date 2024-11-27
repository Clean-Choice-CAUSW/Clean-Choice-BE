package com.cleanChoice.cleanChoice.domain.intakeIngredient.domain.repository;

import com.cleanChoice.cleanChoice.domain.intakeIngredient.domain.IntakeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntakeIngredientRepository extends JpaRepository<IntakeIngredient, Long> {

}
