package com.cleanChoice.cleanChoice.domain.intakeIngredient.service;

import com.cleanChoice.cleanChoice.domain.intakeIngredient.domain.repository.IntakeIngredientRepository;
import com.cleanChoice.cleanChoice.domain.intakeIngredient.dto.request.CreateIntakeIngredientRequestDto;
import com.cleanChoice.cleanChoice.domain.intakeIngredient.dto.request.CreatePassivityIntakeIngredientRequestDto;
import com.cleanChoice.cleanChoice.domain.intakeIngredient.dto.response.IntakeIngredientResponseDto;
import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IntakeIngredientService {

    private final IntakeIngredientRepository intakeIngredientRepository;

    public List<IntakeIngredientResponseDto> getAllIntakeIngredients(Member member) {
        return null;
    }

    public IntakeIngredientResponseDto getIntakeIngredientById(Long intakeIngredientId) {
        return null;
    }

    public IntakeIngredientResponseDto createIntakeIngredient(Member member, CreateIntakeIngredientRequestDto createIntakeIngredientRequestDto) {
        return null;
    }

    public IntakeIngredientResponseDto createIntakeIngredientPassivity(Member member, CreatePassivityIntakeIngredientRequestDto createPassivityIntakeIngredientRequestDto) {
        return null;
    }

    public IntakeIngredientResponseDto deleteIntakeIngredient(Member member, Long intakeIngredientId) {
        return null;
    }


}
