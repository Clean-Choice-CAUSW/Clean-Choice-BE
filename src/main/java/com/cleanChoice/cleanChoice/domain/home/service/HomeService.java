package com.cleanChoice.cleanChoice.domain.home.service;

import com.cleanChoice.cleanChoice.domain.embTest.dto.EmbRequestDto;
import com.cleanChoice.cleanChoice.domain.embTest.dto.EmbResponseDto;
import com.cleanChoice.cleanChoice.domain.embTest.service.FlaskApiService;
import com.cleanChoice.cleanChoice.domain.home.dto.request.AnalyzeRequestDto;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.BanedIngredientInfo;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.CombineUseBanedIngredientInfo;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.Ingredient;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.repository.CombineUseBanedIngredientRepository;
import com.cleanChoice.cleanChoice.domain.ingredient.dto.response.BanedIngredientInfoResponseDto;
import com.cleanChoice.cleanChoice.domain.ingredient.dto.response.CombineUseBanedIngredientInfoResponseDto;
import com.cleanChoice.cleanChoice.domain.ingredient.dto.response.IngredientResponseDto;
import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.product.domain.Product;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductIngredientJoin;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductLabelStatement;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductMarket;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductRepository;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductIngredientJoinResponseDto;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductLabelStatementResponseDto;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductMarketResponseDto;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductResponseDto;
import com.cleanChoice.cleanChoice.global.dtoMapper.DtoMapper;
import com.cleanChoice.cleanChoice.global.dtoMapper.DtoMapperUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final ProductRepository productRepository;

    private final DtoMapperUtil dtoMapperUtil;

    private final FlaskApiService flaskApiService;

    public ProductMarketResponseDto analyzeWithDB(
            Member member,
            AnalyzeRequestDto analyzeRequestDto
    ) {
        List<EmbRequestDto> embRequestDtoList = List.of(
                EmbRequestDto.builder()
                        .productName(analyzeRequestDto.getProductName())
                        .brandName(analyzeRequestDto.getBrandName())
                        .build()
        );
        EmbResponseDto embResponseDto = flaskApiService.getEmbeddingVectorList(embRequestDtoList).get(0);

        if (embResponseDto == null) {
            throw new InternalException("Empty response from Flask API");
        }

        Product product = productRepository.findProductByCosDistanceTop1(
                embResponseDto.getProductNameVectorList().toString(),
                embResponseDto.getBrandNameVectorList().toString()
        );

        //return dtoMapperUtil.toProductMarketResponseDto(product)
        return null;
    }

    public ProductMarketResponseDto resultCorrect(Member member, Long productMarketId, Boolean isCorrect) {
        return null;
    }



}
