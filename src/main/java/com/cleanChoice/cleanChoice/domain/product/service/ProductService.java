package com.cleanChoice.cleanChoice.domain.product.service;

import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.product.domain.PersonalizedInfo;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductMarket;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.PersonalizedInfoRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductMarketRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductRepository;
import com.cleanChoice.cleanChoice.domain.product.dto.request.PersonalizedInfoRequestDto;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductResponseDto;
import com.cleanChoice.cleanChoice.global.dtoMapper.DtoMapperUtil;
import com.cleanChoice.cleanChoice.global.exceptions.BadRequestException;
import com.cleanChoice.cleanChoice.global.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final PersonalizedInfoRepository personalizedInfoRepository;

    private final DtoMapperUtil dtoMapperUtil;
    private final ProductMarketRepository productMarketRepository;

    public ProductResponseDto getProduct(Member member, Long productId) {
        return dtoMapperUtil.toProductResponseDto(
                productRepository.findById(productId)
                        .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST)),
                member
        );
    }

    public ProductResponseDto searchProductByName(Member member, String name) {
        return dtoMapperUtil.toProductResponseDto(
                productRepository.findByName(name)
                        .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST)),
                member
        );
    }

    @Transactional
    public ProductResponseDto makingProduct(
            Member member,
            Long productMarketId,
            PersonalizedInfoRequestDto personalizedInfoRequestDto
    ) {
        ProductMarket productMarket = productMarketRepository.findById(productMarketId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

        personalizedInfoRepository.deleteAll(personalizedInfoRepository.findByProductMarketAndMember(
                productMarket,
                member
        ));

        PersonalizedInfo personalizedInfo = PersonalizedInfo.from(
                productMarket,
                member,
                personalizedInfoRequestDto
        );

        personalizedInfoRepository.save(personalizedInfo);

        return dtoMapperUtil.toProductResponseDto(
                productMarket.getProduct(),
                member
        );
    }

    @Transactional
    public ProductResponseDto deleteMaskingProduct(Member member, Long productMarketId) {
        ProductMarket productMarket = productMarketRepository.findById(productMarketId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

        personalizedInfoRepository.deleteAll(personalizedInfoRepository.findByProductMarketAndMember(
                productMarket,
                member
        ));

        return dtoMapperUtil.toProductResponseDto(
                productMarket.getProduct(),
                member
        );
    }

    @Transactional
    public void insertProductData(MultipartFile file) {

    }

}
