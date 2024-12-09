package com.cleanChoice.cleanChoice.domain.dataInput;

import com.cleanChoice.cleanChoice.domain.dataInput.dto.*;
import com.cleanChoice.cleanChoice.domain.embedding.dto.EmbRequestDto;
import com.cleanChoice.cleanChoice.domain.embedding.dto.EmbResponseDto;
import com.cleanChoice.cleanChoice.domain.embedding.service.FlaskApiService;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.BanType;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.BanedIngredientInfo;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.CombineUseBanedIngredientInfo;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.Ingredient;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.repository.BanedIngredientInfoRepository;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.repository.CombineUseBanedIngredientRepository;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.repository.IngredientRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.*;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.NameBrandNameVectorRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductIngredientJoinRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductLabelStatementRepository;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductRepository;
import com.cleanChoice.cleanChoice.global.exceptions.BadRequestException;
import com.cleanChoice.cleanChoice.global.exceptions.ErrorCode;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataInputService {

    private final ProductRepository productRepository;
    private final NameBrandNameVectorRepository nameBrandNameVectorRepository;

    private final FlaskApiService flaskApiService;
    private final ProductLabelStatementRepository productLabelStatementRepository;
    private final IngredientRepository ingredientRepository;
    private final ProductIngredientJoinRepository productIngredientJoinRepository;
    private final BanedIngredientInfoRepository banedIngredientInfoRepository;
    private final CombineUseBanedIngredientRepository combineUseBanedIngredientRepository;

    private final EntityManager entityManager;

    @Transactional
    public void makeNameBrandNameVector() {
        int page = 0; // 시작 페이지
        int size = 10000; // 페이지 크기
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage;

        do {
            LocalDateTime startTime = LocalDateTime.now();
            // 페이지 단위로 Product 데이터 조회
            productPage = productRepository.findAll(pageable);
            List<Product> productList = productPage.getContent();

            // EmbRequestDto 리스트 생성
            List<EmbRequestDto> embRequestDtoList = productList.stream()
                    .map(product -> new EmbRequestDto(product.getName(), product.getBrandName()))
                    .toList();

            // Flask API 호출하여 EmbResponseDto 리스트 가져오기
            List<EmbResponseDto> embResponseDtoList = flaskApiService.getEmbeddingVectorList(embRequestDtoList);

            // 결과를 기반으로 NameBrandNameVector 생성
            List<NameBrandNameVector> nameBrandNameVectorList = new ArrayList<>();
            for (int i = 0; i < productList.size(); i++) {
                Product product = productList.get(i);
                EmbResponseDto embResponseDto = embResponseDtoList.get(i);

                NameBrandNameVector nameBrandNameVector = NameBrandNameVector.createWithProduct(
                        product,
                        embResponseDto.getProductNameVectorList(),
                        embResponseDto.getBrandNameVectorList()
                );
                nameBrandNameVectorList.add(nameBrandNameVector);
            }

            // 생성된 NameBrandNameVector를 일괄 저장
            if (!nameBrandNameVectorList.isEmpty()) {
                nameBrandNameVectorRepository.saveAll(nameBrandNameVectorList);
            }

            try {
                System.out.println(
                        "[Page #]: " + pageable.getPageNumber() + " / " + productPage.getTotalPages() +
                                " - " + (LocalDateTime.now().getSecond() - startTime.getSecond()) + "s");
            } catch (Exception e) {
                System.out.println(e);
            }

            // 다음 페이지로 이동
            pageable = productPage.nextPageable();
        } while (productPage.hasNext());
    }

    @Transactional
    public void mergeDuplicateNameBrandNameVectors() {
        List<NameBrandNameVector> allVectorList = nameBrandNameVectorRepository.findAll();
        Map<Long, NameBrandNameVector> uniqueVectors = new HashMap<>();
        List<Long> duplicateVectorIds = new ArrayList<>();

        for (NameBrandNameVector vector : allVectorList) {
            Long productId = vector.getProduct().getId();
            if (uniqueVectors.containsKey(productId)) {
                duplicateVectorIds.add(vector.getId()); // 삭제할 ID만 수집
            } else {
                uniqueVectors.put(productId, vector);
            }
        }
        System.out.println("uniqueVectors.size(): " + uniqueVectors.size());

        if (!duplicateVectorIds.isEmpty()) {
            // DELETE 쿼리 실행
            entityManager.createQuery(
                            "DELETE FROM NameBrandNameVector v WHERE v.id IN :ids")
                    .setParameter("ids", duplicateVectorIds)
                    .executeUpdate();
        }
    }

    @Transactional
    public void inputProduct(List<CreateProductDto> createProductDtoList) {
        List<Product> productList = new ArrayList<>();
        List<NameBrandNameVector> nameBrandNameVectorList = new ArrayList<>();
        for (CreateProductDto createProductDto : createProductDtoList) {
            List<Product> existProductList = productRepository.findByDsldId(createProductDto.getDsldId());

            Product product;
            if (existProductList.isEmpty()) {
                product = Product.from(createProductDto);
                EmbResponseDto embResponseDto =  flaskApiService.getEmbeddingVector(
                        createProductDto.getProductName(),
                        createProductDto.getBrandName()
                );
                NameBrandNameVector nameBrandNameVector = NameBrandNameVector.createWithProduct(
                        product,
                        embResponseDto.getProductNameVectorList(),
                        embResponseDto.getBrandNameVectorList()
                );
                nameBrandNameVectorList.add(nameBrandNameVector);

                try { productRepository.saveAndFlush(product);
                } catch (Exception e) {
                    System.out.println("productRepository.saveAndFlush(product) error");
                    System.out.println(e);
                    String prt = product.getDsldId().toString() +
                            product.getDsldUrl() +
                            product.getName() +
                            product.getBrandName() +
                            product.getEnglishProductType() +
                            product.getKoreanProductType() +
                            product.getEnglishSupplementForm() +
                            product.getKoreanSupplementForm() +
                            product.getEnglishSuggestedUse() +
                            product.getKoreanSuggestedUse() +
                            product.getEnglishNetContent() +
                            product.getKoreanNetContent() +
                            product.getEnglishOtherIngredients() +
                            product.getKoreanOtherIngredients();
                    System.out.println(prt);
                }
                nameBrandNameVectorRepository.saveAndFlush(nameBrandNameVector);
            } else {
                product = existProductList.get(0);

                if (!product.getName().equals(createProductDto.getProductName()) ||
                        !product.getBrandName().equals(createProductDto.getBrandName())
                ) {
                    EmbResponseDto embResponseDto =  flaskApiService.getEmbeddingVector(
                            createProductDto.getProductName(),
                            createProductDto.getBrandName()
                    );
                    NameBrandNameVector nameBrandNameVector = NameBrandNameVector.createWithProduct(
                            product,
                            embResponseDto.getProductNameVectorList(),
                            embResponseDto.getBrandNameVectorList()
                    );
                    nameBrandNameVectorList.add(nameBrandNameVector);

                    nameBrandNameVectorRepository.saveAndFlush(nameBrandNameVector);
                }

                product.updateFrom(createProductDto);

                productRepository.saveAndFlush(product);
            }

            productList.add(product);
        }

        //if (!productList.isEmpty()) productRepository.saveAll(productList);
        //if (!nameBrandNameVectorList.isEmpty()) nameBrandNameVectorRepository.saveAll(nameBrandNameVectorList);
    }


    @Transactional
    public void inputProductLabel(List<CreateProductLabelRequestDto> createProductLabelRequestDtoList) {
        List<ProductLabelStatement> productLabelStatementList = new ArrayList<>();

        for (CreateProductLabelRequestDto createProductLabelRequestDto : createProductLabelRequestDtoList) {
            List<Product> productList = productRepository.findByDsldId(createProductLabelRequestDto.getDsldId());
            if (productList.isEmpty()) throw new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST);
            else if (productList.size() > 1) throw new BadRequestException(ErrorCode.ROW_ALREADY_EXIST);

            // TODO: 라벨 타입 지정 필요
            ProductLabelStatement productLabelStatement = ProductLabelStatement.of(
                    productList.get(0),
                    StatementType.of(createProductLabelRequestDto.getStatementType()),
                    createProductLabelRequestDto.getStatement(),
                    ""
            );
            productLabelStatementList.add(productLabelStatement);
        }

        if (!productLabelStatementList.isEmpty()) productLabelStatementRepository.saveAll(productLabelStatementList);
    }

    @Transactional
    public void mergeDuplicateIngredient() {
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        Map<String, Ingredient> uniqueIngredients = new HashMap<>();
        List<Long> duplicateIngredientId = new ArrayList<>();

        for (Ingredient ingredient : ingredientList) {
            String ingredientName = ingredient.getEnglishName();
            if (uniqueIngredients.containsKey(ingredientName)) {
                duplicateIngredientId.add(ingredient.getId());
            } else {
                uniqueIngredients.put(ingredientName, ingredient);
            }
        }

        if (!duplicateIngredientId.isEmpty()) {
            int batchSize = 50000; // 배치 크기 설정 (DB에 따라 조정 가능)
            for (int i = 0; i < duplicateIngredientId.size(); i += batchSize) {
                List<Long> batch = duplicateIngredientId.subList(i, Math.min(i + batchSize, duplicateIngredientId.size()));
                entityManager.createQuery(
                                "DELETE FROM Ingredient i WHERE i.id IN :ids")
                        .setParameter("ids", batch)
                        .executeUpdate();
            }
        }
    }

    @Transactional
    public void inputIngredient(List<CreateIngredientRequestDto> createIngredientRequestDtoList) {
        List<Ingredient> ingredientList = new ArrayList<>();

        for (CreateIngredientRequestDto createIngredientRequestDto : createIngredientRequestDtoList) {
            Ingredient ingredient = ingredientRepository.findByName(createIngredientRequestDto.getIngredientName())
                    .orElse(null);
            if (ingredient == null) {
                ingredient = Ingredient.from(createIngredientRequestDto);
            } else {
                ingredient.updateFrom(createIngredientRequestDto);
            }
            ingredientList.add(ingredient);
        }

        if (!ingredientList.isEmpty()) ingredientRepository.saveAll(ingredientList);
    }

    @Transactional
    public void inputProductIngredientJoin(List<CreateProductIngredientJoinRequestDto> createProductIngredientJoinRequestDtoList) {
        List<ProductIngredientJoin> productIngredientJoinList = new ArrayList<>();

        for (CreateProductIngredientJoinRequestDto createProductIngredientJoinRequestDto : createProductIngredientJoinRequestDtoList) {
            List<Product> productList = productRepository.findByDsldId(createProductIngredientJoinRequestDto.getDsldId());
            if (productList.isEmpty()) throw new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST);
            else if (productList.size() > 1) throw new BadRequestException(ErrorCode.ROW_ALREADY_EXIST);

            Ingredient ingredient = ingredientRepository.findByName(createProductIngredientJoinRequestDto.getIngredientName())
                    .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

            productIngredientJoinList.add(
                    ProductIngredientJoin.from(
                    productList.get(0),
                    ingredient,
                    createProductIngredientJoinRequestDto
                    )
            );
        }

        if (!productIngredientJoinList.isEmpty()) productIngredientJoinRepository.saveAll(productIngredientJoinList);
    }

    @Transactional
    public void inputBanedIngredient(List<CreateBanedIngredientRequestDto> createBanedIngredientRequestDtoList) {
        List<BanedIngredientInfo> banedIngredientInfoList = new ArrayList<>();

        for (CreateBanedIngredientRequestDto createBanedIngredientRequestDto : createBanedIngredientRequestDtoList) {
            Ingredient ingredient = ingredientRepository.findByName(createBanedIngredientRequestDto.getIngredientName())
                    .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

            // TODO: BanType 지정 필요
            banedIngredientInfoList.add(
                    BanedIngredientInfo.of(
                            ingredient,
                            BanType.of(createBanedIngredientRequestDto.getBanType()),
                            createBanedIngredientRequestDto.getDescription()
                    )
            );
        }

        if (!banedIngredientInfoList.isEmpty()) banedIngredientInfoRepository.saveAll(banedIngredientInfoList);
    }

    @Transactional
    public void inputCombineUserBanedIngredient(List<CreateCombineUserBanedIngredientRequestDto> createCombineUserBanedIngredientRequestDtoList) {
        List<CombineUseBanedIngredientInfo> combineUseBanedIngredientInfoList = new ArrayList<>();

        for (CreateCombineUserBanedIngredientRequestDto createCombineUserBanedIngredientRequestDto : createCombineUserBanedIngredientRequestDtoList) {
            Ingredient ingredient = ingredientRepository.findByName(createCombineUserBanedIngredientRequestDto.getIngredientName())
                    .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

            Ingredient combineUseIngredient = ingredientRepository.findByName(createCombineUserBanedIngredientRequestDto.getCombineIngredientName())
                    .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

            combineUseBanedIngredientInfoList.add(
                    CombineUseBanedIngredientInfo.of(
                            ingredient,
                            combineUseIngredient,
                            createCombineUserBanedIngredientRequestDto.getDescription()
                    )
            );
        }

        if (!combineUseBanedIngredientInfoList.isEmpty()) combineUseBanedIngredientRepository.saveAll(combineUseBanedIngredientInfoList);
    }

}
