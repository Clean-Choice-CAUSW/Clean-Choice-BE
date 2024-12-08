package com.cleanChoice.cleanChoice.domain.dataInput.dto;

import com.cleanChoice.cleanChoice.domain.product.domain.StatementType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductLabelRequestDto {

    @NotNull
    private Long dsldId;

    @NotNull
    private String statementType;

    @NotNull
    private String statement;

}
