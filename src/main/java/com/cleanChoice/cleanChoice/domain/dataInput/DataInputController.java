package com.cleanChoice.cleanChoice.domain.dataInput;

import com.cleanChoice.cleanChoice.domain.dataInput.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
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
    @Operation(summary = "상품명, 브랜드명 벡터 생성")
    public void makeNameBrandVector() {
        dataInputService.makeNameBrandNameVector();
    }

    @DeleteMapping("/vector/duplicate")
    @Operation(summary = "상품명, 브랜드명 벡터 중복 제거")
    public void mergeDuplicateNameBrandNameVectors() {
        dataInputService.mergeDuplicateNameBrandNameVectors();
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

    @PostMapping("/ingredient/dulplicate")
    @Operation(summary = "중복 재료 병합")
    public void mergeDuplicateIngredient() {
        dataInputService.mergeDuplicateIngredient();
    }

    @PostMapping(value = "/product/ingredient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "product-ingredient join 연결 정보 입력")
    public void inputProductIngredientJoin(
            @RequestPart("file") MultipartFile file
    ) {
        try {
            String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();

            List<CreateProductIngredientJoinRequestDto> createProductIngredientJoinRequestDtoList = Arrays.asList(
                    objectMapper.readValue(fileContent, CreateProductIngredientJoinRequestDto[].class)
            );

            dataInputService.inputProductIngredientJoin(createProductIngredientJoinRequestDtoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/ingredient/baned", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void inputBanedIngredient(
            @RequestPart("file") MultipartFile file
    ) {
        try {
            String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();

            List<CreateBanedIngredientRequestDto> createBanedIngredientRequestDtoList = Arrays.asList(
                    objectMapper.readValue(fileContent, CreateBanedIngredientRequestDto[].class)
            );

            dataInputService.inputBanedIngredient(createBanedIngredientRequestDtoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/ingredient/baned/combine-use")
    public void inputCombineUserBanedIngredient(
            @RequestPart("file") MultipartFile file
    ) {
        try {
            String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();

            List<CreateCombineUserBanedIngredientRequestDto> createCombineUserBanedIngredientRequestDtoList = Arrays.asList(
                    objectMapper.readValue(fileContent, CreateCombineUserBanedIngredientRequestDto[].class)
            );

            dataInputService.inputCombineUserBanedIngredient(createCombineUserBanedIngredientRequestDtoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/ingredient/set-lowwer")
    public void setIngredientNameLower() {
        dataInputService.setIngredientNameLower();
    }

    @PostMapping(value = "/ingredient/effectiveness", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void setIngredientEffectiveness(
            @RequestPart("file") MultipartFile file
    ) {
        try {
            String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();

            List<CreateIngredientEffectivenessRequestDto> createIngredientEffectivenessRequestDtoList = Arrays.asList(
                    objectMapper.readValue(fileContent, CreateIngredientEffectivenessRequestDto[].class)
            );

            dataInputService.setIngredientEffectiveness(createIngredientEffectivenessRequestDtoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
