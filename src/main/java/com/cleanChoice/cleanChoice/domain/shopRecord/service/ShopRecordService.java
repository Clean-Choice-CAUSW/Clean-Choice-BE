package com.cleanChoice.cleanChoice.domain.shopRecord.service;

import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.shopRecord.dto.response.ShopRecordResponseDto;
import com.cleanChoice.cleanChoice.domain.shopRecord.service.repository.ShopRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopRecordService {

    private final ShopRecordRepository shopRecordRepository;

    public ShopRecordResponseDto createShopRecord(Member member, Long productMarketId) {
        return null;
    }

    public Page<ShopRecordResponseDto> getShopRecordAllByPage(Member member, Pageable pageable) {
        return null;
    }

    public ShopRecordResponseDto getShopRecord(Member member, Long shopRecordId) {
        return null;
    }

    public ShopRecordResponseDto deleteShopRecord(Member member, Long shopRecordId) {
        return null;
    }

    public void deleteShopRecordAll(Member member) {
    }
}
