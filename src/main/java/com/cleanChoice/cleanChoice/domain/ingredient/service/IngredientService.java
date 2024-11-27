package com.cleanChoice.cleanChoice.domain.ingredient.service;

import com.cleanChoice.cleanChoice.domain.ingredient.domain.repository.BanedIngredientInfoRepository;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.repository.CombineUseBanedIngredientRepository;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.repository.IngredientRepository;
import com.cleanChoice.cleanChoice.domain.ingredient.dto.response.IngredientResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final BanedIngredientInfoRepository banedIngredientInfoRepository;
    private final CombineUseBanedIngredientRepository combineUseBanedIngredientRepository;


    public IngredientResponseDto getIngredientById(Long ingredientId) {
        return null;
    }
}
