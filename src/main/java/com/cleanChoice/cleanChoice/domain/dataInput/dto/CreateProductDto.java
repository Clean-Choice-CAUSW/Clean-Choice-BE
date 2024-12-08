package com.cleanChoice.cleanChoice.domain.dataInput.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {

    @JsonProperty("dsldId")
    @NotNull
    private Long dsldId;

    @JsonProperty("dsldUrl")
    @NotBlank
    private String dsldUrl;

    @JsonProperty("productName")
    @NotNull
    private String productName;

    @JsonProperty("brandName")
    @NotNull
    private String brandName;

    @JsonProperty("englishNetContent")
    @NotNull
    private String englishNetContent;

    @JsonProperty("koreanNetContent")
    @NotNull
    private String koreanNetContent;

    @JsonProperty("servingSize")
    @NotNull
    private String servingSize;

    @JsonProperty("englishProductType")
    @NotNull
    private String englishProductType;

    @JsonProperty("koreanProductType")
    @NotNull
    private String koreanProductType;

    @JsonProperty("englishSupplementForm")
    @NotNull
    private String englishSupplementForm;

    @JsonProperty("koreanSupplementForm")
    @NotNull
    private String koreanSupplementForm;

    @JsonProperty("englishSuggestedUse")
    @NotNull
    private String englishSuggestedUse;

    @JsonProperty("koreanSuggestedUse")
    @NotNull
    private String koreanSuggestedUse;

    @JsonProperty("country")
    @NotNull
    private String country;

    @JsonProperty("otherIngredients")
    @NotNull
    private String otherIngredients;

}
