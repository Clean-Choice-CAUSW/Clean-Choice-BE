package com.cleanChoice.cleanChoice.domain.home.service;

import com.cleanChoice.cleanChoice.domain.home.dto.request.AnalyzeRequestDto;
import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductMarketResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeService {

    public ProductMarketResponseDto analyzeWithDB(
            Member member,
            AnalyzeRequestDto analyzeRequestDto
    ) {
        return null;
    }

    public ProductMarketResponseDto resultCorrect(Member member, Long productMarketId, Boolean isCorrect) {
        return null;
    }
}
