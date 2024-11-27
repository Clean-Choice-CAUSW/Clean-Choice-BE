package com.cleanChoice.cleanChoice.domain.shopRecord.controller;

import com.cleanChoice.cleanChoice.domain.shopRecord.dto.response.ShopRecordResponseDto;
import com.cleanChoice.cleanChoice.domain.shopRecord.service.ShopRecordService;
import com.cleanChoice.cleanChoice.global.config.security.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/shop-record")
@RequiredArgsConstructor
public class ShopRecordController {

    private final ShopRecordService shopRecordService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "쇼핑 기록 생성", description = "쇼핑 기록을 생성합니다." +
            "쇼핑 기록 생성 후, 해당 쇼핑 기록에 해당하는 ingredient를 복용 중으로 자동으로 추가합니다.")
    public ShopRecordResponseDto createShopRecord(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestHeader Long productMarketId
    ) {
        return shopRecordService.createShopRecord(customUserDetails.getMember(), productMarketId);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "쇼핑 기록 전체 조회", description = "쇼핑 기록을 전체 조회합니다.")
    public Page<ShopRecordResponseDto> getShopRecordAllByPage(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestHeader Pageable pageable
    ) {
        return shopRecordService.getShopRecordAllByPage(customUserDetails.getMember(), pageable);
    }

    @GetMapping("/{shopRecordId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "쇼핑 기록 조회", description = "쇼핑 기록을 조회합니다.")
    public ShopRecordResponseDto getShopRecord(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long shopRecordId
    ) {
        return shopRecordService.getShopRecord(customUserDetails.getMember(), shopRecordId);
    }

    @DeleteMapping("/{shopRecordId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "쇼핑 기록 삭제", description = "쇼핑 기록을 삭제합니다.")
    public ShopRecordResponseDto deleteShopRecord(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long shopRecordId
    ) {
        return shopRecordService.deleteShopRecord(customUserDetails.getMember(), shopRecordId);
    }

    @PutMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "쇼핑 기록 전체 삭제", description = "쇼핑 기록을 전체 삭제합니다.")
    public void deleteShopRecordAll(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        shopRecordService.deleteShopRecordAll(customUserDetails.getMember());
    }



}
