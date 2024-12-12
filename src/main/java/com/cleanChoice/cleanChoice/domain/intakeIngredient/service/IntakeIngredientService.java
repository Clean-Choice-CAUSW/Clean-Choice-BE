package com.cleanChoice.cleanChoice.domain.intakeIngredient.service;

import com.cleanChoice.cleanChoice.domain.ingredient.domain.repository.IngredientRepository;
import com.cleanChoice.cleanChoice.domain.intakeIngredient.domain.IntakeIngredient;
import com.cleanChoice.cleanChoice.domain.intakeIngredient.domain.repository.IntakeIngredientRepository;
import com.cleanChoice.cleanChoice.domain.intakeIngredient.dto.request.CreateIntakeIngredientRequestDto;
import com.cleanChoice.cleanChoice.domain.intakeIngredient.dto.request.CreatePassivityIntakeIngredientRequestDto;
import com.cleanChoice.cleanChoice.domain.intakeIngredient.dto.response.IntakeIngredientResponseDto;
import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.global.dtoMapper.DtoMapperUtil;
import com.cleanChoice.cleanChoice.global.exceptions.BadRequestException;
import com.cleanChoice.cleanChoice.global.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IntakeIngredientService {

    private final IntakeIngredientRepository intakeIngredientRepository;

    private final DtoMapperUtil dtoMapperUtil;
    private final IngredientRepository ingredientRepository;

    public List<IntakeIngredientResponseDto> getAllIntakeIngredients(Member member) {
        List<IntakeIngredient> ingredientList = intakeIngredientRepository.findAllByMember(member);

        return ingredientList.stream()
                .map(dtoMapperUtil::toIntakeIngredientResponseDto)
                .toList();
    }

    public IntakeIngredientResponseDto getIntakeIngredientById(Long intakeIngredientId) {
        return dtoMapperUtil.toIntakeIngredientResponseDto(
                intakeIngredientRepository.findById(intakeIngredientId)
                        .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST))
        );
    }

    @Transactional
    public IntakeIngredientResponseDto createIntakeIngredient(Member member, CreateIntakeIngredientRequestDto createIntakeIngredientRequestDto) {
        return dtoMapperUtil.toIntakeIngredientResponseDto(
            intakeIngredientRepository.save(
                    IntakeIngredient.createWithIngredient(
                            member,
                            ingredientRepository.findById(createIntakeIngredientRequestDto.getIngredientId())
                                    .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST)),
                            null,
                            null
                    )
            )
        );
    }

    @Transactional
    public IntakeIngredientResponseDto createIntakeIngredientPassivity(Member member, CreatePassivityIntakeIngredientRequestDto createPassivityIntakeIngredientRequestDto) {
        return dtoMapperUtil.toIntakeIngredientResponseDto(
                intakeIngredientRepository.save(
                        IntakeIngredient.createWithFakeName(
                                member,
                                createPassivityIntakeIngredientRequestDto.getFakeName(),
                                createPassivityIntakeIngredientRequestDto.getAmount(),
                                createPassivityIntakeIngredientRequestDto.getUnit()
                        )
                )
        );
    }

    @Transactional
    public IntakeIngredientResponseDto deleteIntakeIngredient(Member member, Long intakeIngredientId) {
        IntakeIngredient intakeIngredient = intakeIngredientRepository.findById(intakeIngredientId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

        if (!intakeIngredient.getMember().getId().equals(member.getId())) {
            throw new BadRequestException(ErrorCode.API_NOT_ACCESSIBLE);
        }

        intakeIngredientRepository.delete(intakeIngredient);

        return dtoMapperUtil.toIntakeIngredientResponseDto(intakeIngredient);
    }


}
