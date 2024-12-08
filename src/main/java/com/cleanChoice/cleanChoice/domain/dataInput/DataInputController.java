package com.cleanChoice.cleanChoice.domain.dataInput;

import com.cleanChoice.cleanChoice.domain.dataInput.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dataInput")
@RequiredArgsConstructor
public class DataInputController {

    private final DataInputService dataInputService;

    @PostMapping("/vector")
    public void makeNameBrandVector() {
        dataInputService.makeNameBrandNameVector();
    }

    @PostMapping(value = "/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void inputProduct(
            @RequestPart("file") MultipartFile file
    ) {
        try {
            String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();

            List<CreateProductDto> createProductDtoList = Arrays.asList(
                    objectMapper.readValue(fileContent, CreateProductDto[].class)
            );

            dataInputService.inputProduct(createProductDtoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/product/label")
    public void inputProductLabel(
            @RequestBody @Valid List<CreateProductLabelRequestDto> createProductLabelRequestDtoList
    ) {
        dataInputService.inputProductLabel(createProductLabelRequestDtoList);
    }

    @PostMapping("/ingredient")
    public void inputIngredient(
            @RequestBody @Valid List<CreateIngredientRequestDto> createIngredientRequestDtoList
    ) {
        dataInputService.inputIngredient(createIngredientRequestDtoList);
    }

    @PostMapping("/product/ingredient")
    public void inputProductIngredientJoin(
            @RequestBody @Valid List<CreateProductIngredientJoinRequestDto> createProductIngredientJoinRequestDtoList
    ) {
        dataInputService.inputProductIngredientJoin(createProductIngredientJoinRequestDtoList);
    }

    @PostMapping("/ingredient/baned")
    public void inputBanedIngredient(
            @RequestBody @Valid List<CreateBanedIngredientRequestDto> createBanedIngredientRequestDtoList
    ) {
        dataInputService.inputBanedIngredient(createBanedIngredientRequestDtoList);
    }

    @PostMapping("/ingredient/baned/combine-use")
    public void inputCombineUserBanedIngredient(
            @RequestBody @Valid List<CreateCombineUserBanedIngredientRequestDto> createCombineUserBanedIngredientRequestDtoList
    ) {
        dataInputService.inputCombineUserBanedIngredient(createCombineUserBanedIngredientRequestDtoList);
    }

}
