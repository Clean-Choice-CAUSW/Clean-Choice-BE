package com.cleanChoice.cleanChoice.domain.embTest.service;

import com.cleanChoice.cleanChoice.domain.embTest.dto.EmbAnalyzeResponseDto;
import com.cleanChoice.cleanChoice.domain.embTest.dto.EmbRequestDto;
import com.cleanChoice.cleanChoice.domain.embTest.dto.EmbResponseDto;
import com.cleanChoice.cleanChoice.domain.embTest.domain.EmbTest;
import com.cleanChoice.cleanChoice.domain.embTest.domain.EmbTestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmbService {

    private final EmbTestRepository embTestRepository;
    private final FlaskApiService flaskApiService;

    @Transactional
    public void createEmbList(List<EmbRequestDto> embRequestDtoList) {
        List<EmbResponseDto> embResponseDtoList = flaskApiService.getEmbeddingVectorList(embRequestDtoList);

        List<EmbTest> embTestList = new ArrayList<>();
        embResponseDtoList.forEach(embResponseDto -> {
            float[] nameVector = new float[embResponseDto.getProductNameVectorList().size()];
            float[] brandNameVector = new float[embResponseDto.getBrandNameVectorList().size()];

            for (int i = 0; i < embResponseDto.getProductNameVectorList().size(); i++)
                nameVector[i] = embResponseDto.getProductNameVectorList().get(i);
            for (int i = 0; i < embResponseDto.getBrandNameVectorList().size(); i++)
                brandNameVector[i] = embResponseDto.getBrandNameVectorList().get(i);

            embTestList.add(
                    EmbTest.builder()
                            .productName(embResponseDto.getProductName())
                            .brandName(embResponseDto.getBrandName())
                            .nameVector(nameVector)
                            .brandNameVector(brandNameVector)
                            .build()
            );
        });

        embTestRepository.saveAll(embTestList);
    }

    public List<EmbAnalyzeResponseDto> analyze(EmbRequestDto embRequestDto) {
        List<EmbRequestDto> embRequestDtoList = new ArrayList<>();
        embRequestDtoList.add(embRequestDto);
        List<EmbResponseDto> analysisResultList = flaskApiService.getEmbeddingVectorList(embRequestDtoList);
        EmbResponseDto analysisResult = analysisResultList.get(0);

        List<EmbTest> embTestList = embTestRepository.findAllCos(
                analysisResult.getProductNameVectorList().toString(),
                analysisResult.getBrandNameVectorList().toString()
        );

        List<EmbAnalyzeResponseDto> embAnalyzeResponseDtoList = embTestList
                .stream()
                .map(embTest -> {
                    List<Float> nameVectorList = new ArrayList<>();
                    List<Float> brandNameVectorList = new ArrayList<>();
                    for (float v : embTest.getNameVector()) {
                        nameVectorList.add(v);
                    }
                    for (float v : embTest.getBrandNameVector()) {
                        brandNameVectorList.add(v);
                    }
                    return EmbAnalyzeResponseDto.builder()
                            .productName(embTest.getProductName())
                            .brandName(embTest.getBrandName())
                            //.productNameVectorList(nameVectorList)
                            //.brandNameVectorList(brandNameVectorList)
                            .nameDistance(embTest.getNameDistance())
                            .brandNameDistance(embTest.getBrandNameDistance())
                            .build();
                        }
                ).toList();

        return embAnalyzeResponseDtoList;
    }


}
