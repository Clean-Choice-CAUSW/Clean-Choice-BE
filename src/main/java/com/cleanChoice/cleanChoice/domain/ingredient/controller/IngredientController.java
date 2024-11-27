package com.cleanChoice.cleanChoice.domain.ingredient.controller;

import com.cleanChoice.cleanChoice.domain.ingredient.dto.response.IngredientResponseDto;
import com.cleanChoice.cleanChoice.domain.ingredient.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ingredient")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("{ingredientId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "성분 상세 조회", description = "성분 상세 정보를 조회합니다.")
    public IngredientResponseDto getIngredientById(
            @PathVariable Long ingredientId
    ) {
        return ingredientService.getIngredientById(ingredientId);
    }

}
