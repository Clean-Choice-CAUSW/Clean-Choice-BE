package com.cleanChoice.cleanChoice.domain.viewRecord.service;

import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.viewRecord.domain.repository.ViewRecordRepository;
import com.cleanChoice.cleanChoice.domain.viewRecord.dto.response.ViewRecordResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewRecordService {

    private final ViewRecordRepository viewRecordRepository;


    public List<ViewRecordResponseDto> getViewRecordList(Member member) {
        return null;
    }

    public ViewRecordResponseDto deleteViewRecord(Member member, Long viewRecordId) {
        return null;
    }

    public List<ViewRecordResponseDto> deleteAllViewRecord(Member member) {
        return null;
    }
}
