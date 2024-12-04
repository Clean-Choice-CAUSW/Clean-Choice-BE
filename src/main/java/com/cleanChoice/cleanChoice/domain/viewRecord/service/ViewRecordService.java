package com.cleanChoice.cleanChoice.domain.viewRecord.service;

import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductMarket;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductMarketRepository;
import com.cleanChoice.cleanChoice.domain.viewRecord.domain.ViewRecord;
import com.cleanChoice.cleanChoice.domain.viewRecord.domain.repository.ViewRecordRepository;
import com.cleanChoice.cleanChoice.domain.viewRecord.dto.response.ViewRecordResponseDto;
import com.cleanChoice.cleanChoice.global.dtoMapper.DtoMapperUtil;
import com.cleanChoice.cleanChoice.global.exceptions.BadRequestException;
import com.cleanChoice.cleanChoice.global.exceptions.ErrorCode;
import com.cleanChoice.cleanChoice.global.util.StaticValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ViewRecordService {

    private final ViewRecordRepository viewRecordRepository;

    private final DtoMapperUtil dtoMapperUtil;
    private final ProductMarketRepository productMarketRepository;

    public List<ViewRecordResponseDto> getViewRecordList(Member member) {
        return viewRecordRepository.findByMemberOrderByCreatedAtDesc(member)
                .stream()
                .map(dtoMapperUtil::toViewRecordResponseDto)
                .toList();
    }

    @Transactional
    public ViewRecordResponseDto createViewRecord(Member member, ProductMarket productMarket) {
        productMarket.updateViewCount();
        productMarketRepository.save(productMarket);

        List<ViewRecord> viewRecordList = viewRecordRepository.findByMemberOrderByCreatedAtDesc(member);

        if (viewRecordList.size() >= StaticValue.MAX_VIEW_RECORD_SIZE) {
            viewRecordRepository.delete(viewRecordList.get(viewRecordList.size() - 1));
        }

        return dtoMapperUtil.toViewRecordResponseDto(
                viewRecordRepository.save(
                        ViewRecord.of(
                                member,
                                productMarket
                        )
                )
        );
    }

    @Transactional
    public ViewRecordResponseDto deleteViewRecord(Member member, Long viewRecordId) {
        ViewRecord viewRecord = viewRecordRepository.findById(viewRecordId).orElseThrow();

        if (!viewRecord.getMember().equals(member)) {
            throw new BadRequestException(ErrorCode.API_NOT_ACCESSIBLE);
        }

        viewRecordRepository.delete(viewRecord);

        return dtoMapperUtil.toViewRecordResponseDto(viewRecord);
    }

    @Transactional
    public List<ViewRecordResponseDto> deleteAllViewRecord(Member member) {
        List<ViewRecord> viewRecordList = viewRecordRepository.findByMemberOrderByCreatedAtDesc(member);

        viewRecordRepository.deleteAll(viewRecordList);

        return viewRecordList.stream()
                .map(dtoMapperUtil::toViewRecordResponseDto)
                .toList();
    }

}
