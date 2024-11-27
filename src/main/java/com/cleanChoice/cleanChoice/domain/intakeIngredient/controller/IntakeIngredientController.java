package com.cleanChoice.cleanChoice.domain.intakeIngredient.controller;

import com.cleanChoice.cleanChoice.domain.intakeIngredient.dto.request.CreateIntakeIngredientRequestDto;
import com.cleanChoice.cleanChoice.domain.intakeIngredient.dto.request.CreatePassivityIntakeIngredientRequestDto;
import com.cleanChoice.cleanChoice.domain.intakeIngredient.dto.response.IntakeIngredientResponseDto;
import com.cleanChoice.cleanChoice.domain.intakeIngredient.service.IntakeIngredientService;
import com.cleanChoice.cleanChoice.global.config.security.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/intake-ingredient")
@RequiredArgsConstructor
public class IntakeIngredientController {

    private final IntakeIngredientService intakeIngredientService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "유저 섭취 성분 전체 조회", description = "유저 섭취 성분 전체 정보를 조회합니다.")
    public List<IntakeIngredientResponseDto> getAllIntakeIngredients(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return intakeIngredientService.getAllIntakeIngredients(customUserDetails.getMember());
    }

    @GetMapping("{intakeIngredientId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "유저 섭취 성분 상세 조회", description = "섭취 성분 상세 정보를 조회합니다.")
    public IntakeIngredientResponseDto getIntakeIngredientById(
            @PathVariable Long intakeIngredientId
    ) {
        return intakeIngredientService.getIntakeIngredientById(intakeIngredientId);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "유저 섭취 성분 생성", description = "유저 섭취 성분을 생성합니다.")
    public IntakeIngredientResponseDto createIntakeIngredient(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody CreateIntakeIngredientRequestDto createIntakeIngredientRequestDto
    ) {
        return intakeIngredientService.createIntakeIngredient(customUserDetails.getMember(), createIntakeIngredientRequestDto);
    }

    @PostMapping("/create/passivity")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "유저 섭취 성분 생성(수동)", description = "만약 현재 서비스 DB에 유저가 등록하고자 하는 성분이 없을 시 유저 섭취 성분을 생성합니다." +
            "이럴 경우 성분에 대한 분석은 서비스에서 제공되지 않습니다.")
    public IntakeIngredientResponseDto createIntakeIngredientPassivity(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody CreatePassivityIntakeIngredientRequestDto createPassivityIntakeIngredientRequestDto
    ) {
        return intakeIngredientService.createIntakeIngredientPassivity(customUserDetails.getMember(), createPassivityIntakeIngredientRequestDto);
    }

    @DeleteMapping("{intakeIngredientId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "유저 섭취 성분 삭제", description = "유저 섭취 성분을 삭제합니다.")
    public IntakeIngredientResponseDto deleteIntakeIngredient(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long intakeIngredientId
    ) {
        return intakeIngredientService.deleteIntakeIngredient(customUserDetails.getMember(), intakeIngredientId);
    }

}
