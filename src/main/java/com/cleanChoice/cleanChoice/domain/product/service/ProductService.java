package com.cleanChoice.cleanChoice.domain.product.service;

import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductRepository;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductResponseDto;
import com.cleanChoice.cleanChoice.global.dtoMapper.DtoMapperUtil;
import com.cleanChoice.cleanChoice.global.exceptions.BadRequestException;
import com.cleanChoice.cleanChoice.global.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    private final DtoMapperUtil dtoMapperUtil;

    public ProductResponseDto getProduct(Long productId) {
        return dtoMapperUtil.toProductResponseDto(
                productRepository.findById(productId)
                        .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST))
        );
    }

    public ProductResponseDto searchProductByName(String name) {
        return dtoMapperUtil.toProductResponseDto(
                productRepository.findByName(name)
                        .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST))
        );
    }
}
