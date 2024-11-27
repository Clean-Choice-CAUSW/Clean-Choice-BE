package com.cleanChoice.cleanChoice.domain.product.service;

import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductIngredientJoinRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductLabelStatementRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductMarketRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductRepository;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductIngredientJoinRepository productIngredientJoinRepository;
    private final ProductLabelStatementRepository productLabelStatementRepository;
    private final ProductMarketRepository productMarketRepository;

    public ProductResponseDto getProduct(String productId) {
        return null;
    }
}
