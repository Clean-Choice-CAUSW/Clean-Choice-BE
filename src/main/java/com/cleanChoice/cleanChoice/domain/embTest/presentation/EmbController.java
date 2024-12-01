package com.cleanChoice.cleanChoice.domain.embTest.presentation;

import com.cleanChoice.cleanChoice.domain.embTest.service.EmbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
