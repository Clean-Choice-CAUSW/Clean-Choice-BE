package com.cleanChoice.cleanChoice.domain.embTest.presentation;

import com.cleanChoice.cleanChoice.domain.embTest.dto.AnalyzeResponseDto;
import com.cleanChoice.cleanChoice.domain.embTest.dto.EmbRequestDto;
import com.cleanChoice.cleanChoice.domain.embTest.dto.EmbResponseDto;
import com.cleanChoice.cleanChoice.domain.embTest.service.EmbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/emb")
@RequiredArgsConstructor
public class EmbController {

    private final EmbService embService;

    /*
    @PostMapping("/create-list")
    public void createEmbList(
            @RequestBody List<EmbRequestDto> embRequestDtoList
    ) {
        embService.
                createEmbList(embRequestDtoList);
    }

    @PostMapping("/analyze")
    public List<AnalyzeResponseDto> analyze(
            @RequestBody EmbRequestDto embRequestDto
    ) {
        return embService.analyze(embRequestDto);
    }

     */

}
