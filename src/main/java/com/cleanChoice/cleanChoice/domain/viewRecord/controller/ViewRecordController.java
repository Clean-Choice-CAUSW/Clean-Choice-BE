package com.cleanChoice.cleanChoice.domain.viewRecord.controller;

import com.cleanChoice.cleanChoice.domain.viewRecord.service.ViewRecordService;
import com.cleanChoice.cleanChoice.domain.viewRecord.dto.response.ViewRecordResponseDto;
import com.cleanChoice.cleanChoice.global.config.security.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/view-record")
@RequiredArgsConstructor
public class ViewRecordController {

    private final ViewRecordService viewRecordService;

    @GetMapping("/list")
    @Operation(summary = "최근 본 상품 리스트", description = "현재 로그인한 사용자의 최근 본 상품 리스트를 반환합니다." +
            "최대 30개이며, 본 상품이 없을 시 빈 리스트 반환이고, 최근 추가된 순서대로 반환합니다.")
    public List<ViewRecordResponseDto> getViewRecordList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return viewRecordService.getViewRecordList(customUserDetails.getMember());
    }

    @DeleteMapping("/{viewRecordId}")
    @Operation(summary = "최근 본 상품 삭제", description = "최근 본 상품을 삭제합니다.")
    public ViewRecordResponseDto deleteViewRecord(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long viewRecordId
    ) {
        return viewRecordService.deleteViewRecord(customUserDetails.getMember(), viewRecordId);
    }

    @DeleteMapping("/all")
    @Operation(summary = "최근 본 상품 전체 삭제", description = "최근 본 상품을 전체 삭제합니다.")
    public List<ViewRecordResponseDto> deleteAllViewRecord(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return viewRecordService.deleteAllViewRecord(customUserDetails.getMember());
    }

}
