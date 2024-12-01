package com.cleanChoice.cleanChoice.domain.intakeIngredient.domain.repository;

import com.cleanChoice.cleanChoice.domain.intakeIngredient.domain.IntakeIngredient;
import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntakeIngredientRepository extends JpaRepository<IntakeIngredient, Long> {

    List<IntakeIngredient> findAllByMember(Member member);
}
