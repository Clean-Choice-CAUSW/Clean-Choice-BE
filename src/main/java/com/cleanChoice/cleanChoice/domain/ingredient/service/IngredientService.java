package com.cleanChoice.cleanChoice.domain.ingredient.service;

import com.cleanChoice.cleanChoice.domain.ingredient.domain.Ingredient;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.repository.IngredientRepository;
import com.cleanChoice.cleanChoice.domain.ingredient.dto.response.IngredientResponseDto;
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
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    private final DtoMapperUtil dtoMapperUtil;


    public IngredientResponseDto getIngredientById(Long ingredientId) {
        return dtoMapperUtil.toIngredientResponseDto(
                ingredientRepository.findById(ingredientId)
                        .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST))
        );
    }

    public List<IngredientResponseDto> searchIngredientByName(String name) {
        List<Ingredient> ingredientList = ingredientRepository.findAllByName(name);

        return ingredientList
                .stream()
                .map(dtoMapperUtil::toIngredientResponseDto)
                .toList();
    }

    public List<IngredientResponseDto> searchIngredientByNamePart(String name) {
        List<Ingredient> ingredientList = ingredientRepository.findByNamePart(name);

        return ingredientList
                .stream()
                .map(dtoMapperUtil::toIngredientResponseDto)
                .toList();
    }

}
