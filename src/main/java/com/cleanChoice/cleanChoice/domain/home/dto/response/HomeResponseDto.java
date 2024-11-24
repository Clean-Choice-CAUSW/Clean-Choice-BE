package com.cleanChoice.cleanChoice.domain.home.dto.response;

import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductMarketResponseDto;
import com.cleanChoice.cleanChoice.domain.product.dto.response.ProductResponseDto;
import lombok.*;

@Getter
@Setter
@Builder
public class HomeResponseDto {

    private ProductMarketResponseDto productMarketResponseDto;

}
