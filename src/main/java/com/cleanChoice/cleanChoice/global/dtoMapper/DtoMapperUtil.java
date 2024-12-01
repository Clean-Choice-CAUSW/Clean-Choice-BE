package com.cleanChoice.cleanChoice.global.dtoMapper;

import com.cleanChoice.cleanChoice.domain.home.dto.response.AnalyzeResponseDto;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.BanedIngredientInfo;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.CombineUseBanedIngredientInfo;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.Ingredient;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.repository.CombineUseBanedIngredientRepository;
import com.cleanChoice.cleanChoice.domain.ingredient.dto.response.BanedIngredientInfoResponseDto;
import com.cleanChoice.cleanChoice.domain.ingredient.dto.response.CombineUseBanedIngredientInfoResponseDto;
import com.cleanChoice.cleanChoice.domain.ingredient.dto.response.IngredientResponseDto;
import com.cleanChoice.cleanChoice.domain.intakeIngredient.domain.IntakeIngredient;
import com.cleanChoice.cleanChoice.domain.intakeIngredient.dto.response.IntakeIngredientResponseDto;
import com.cleanChoice.cleanChoice.domain.product.domain.Product;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductIngredientJoin;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductLabelStatement;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductMarket;
import com.cleanChoice.cleanChoice.domain.product.dto.response.*;
import com.cleanChoice.cleanChoice.domain.shopBasket.domain.ShopBasket;
import com.cleanChoice.cleanChoice.domain.shopBasket.domain.ShopBasketProductJoin;
import com.cleanChoice.cleanChoice.domain.shopBasket.dto.response.ShopBasketProductJoinResponseDto;
import com.cleanChoice.cleanChoice.domain.shopBasket.dto.response.ShopBasketResponseDto;
import com.cleanChoice.cleanChoice.domain.shopRecord.domain.ShopRecord;
import com.cleanChoice.cleanChoice.domain.shopRecord.dto.response.ShopRecordResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoMapperUtil {

    private final CombineUseBanedIngredientRepository combineUseBanedIngredientRepository;

    public AnalyzeResponseDto toAnalyzeResponseDto(ProductMarket productMarket, AnalyzeType analyzeType) {
        return DtoMapper.INSTANCE.toAnalyzeResponseDto(
                productMarket,
                toProductResponseDto(productMarket.getProduct()),
                analyzeType
        );
    }

    public ProductMarketResponseDto toProductMarketResponseDto(ProductMarket productMarket) {
        return DtoMapper.INSTANCE.toProductMarketResponseDto(
                productMarket,
                toProductResponseDto(productMarket.getProduct())
        );
    }

    public ProductResponseDto toProductResponseDto(Product product) {
        return DtoMapper.INSTANCE.toProductResponseDto(
                product,
                product.getProductIngredientJoinList()
                        .stream().map(this::toProductIngredientJoinResponseDto).toList(),
                product.getProductLabelStatementList()
                        .stream().map(this::toProductLabelStatementResponseDto).toList()
        );
    }

    public ProductIngredientJoinResponseDto toProductIngredientJoinResponseDto(ProductIngredientJoin productIngredientJoin) {
        return DtoMapper.INSTANCE.toProductIngredientJoinResponseDto(
                productIngredientJoin,
                toIngredientResponseDto(productIngredientJoin.getIngredient())
        );
    }

    public IngredientResponseDto toIngredientResponseDto(Ingredient ingredient) {
        return DtoMapper.INSTANCE.toIngredientResponseDto(
                ingredient,
                ingredient.getBanedIngredientInfoList()
                        .stream().map(this::toBanedIngredientInfoResponseDto).toList(),
                combineUseBanedIngredientRepository.findAllByIngredientAndCombineIngredient(ingredient)
                        .stream().map(this::toCombineUseBanedIngredientInfoResponseDto).toList()
        );
    }

    public BanedIngredientInfoResponseDto toBanedIngredientInfoResponseDto(BanedIngredientInfo banedIngredientInfo) {
        return DtoMapper.INSTANCE.toBanedIngredientInfoResponseDto(banedIngredientInfo);
    }

    public CombineUseBanedIngredientInfoResponseDto toCombineUseBanedIngredientInfoResponseDto(CombineUseBanedIngredientInfo combineUseBanedIngredientInfo) {
        return DtoMapper.INSTANCE.toCombineUseBanedIngredientInfoResponseDto(combineUseBanedIngredientInfo);
    }

    public ProductLabelStatementResponseDto toProductLabelStatementResponseDto(ProductLabelStatement productLabelStatement) {
        return DtoMapper.INSTANCE.toProductLabelStatementResponseDto(productLabelStatement);
    }

    public IntakeIngredientResponseDto toIntakeIngredientResponseDto(IntakeIngredient intakeIngredient) {
        return DtoMapper.INSTANCE.toIntakeIngredientResponseDto(
                intakeIngredient,
                intakeIngredient.getIngredient() == null ?
                        null :
                        toIngredientResponseDto(intakeIngredient.getIngredient())
        );
    }

    public ShopBasketResponseDto toShopBasketResponseDto(ShopBasket shopBasket) {
        return DtoMapper.INSTANCE.toShopBasketResponseDto(
                shopBasket,
                shopBasket.getShopBasketProductJoinList()
                        .stream().map(this::toShopBasketProductJoinResponseDto).toList()
        );
    }

    public ShopBasketProductJoinResponseDto toShopBasketProductJoinResponseDto(ShopBasketProductJoin shopBasketProductJoin) {
        return DtoMapper.INSTANCE.toShopBasketProductJoinResponseDto(
                shopBasketProductJoin,
                toProductMarketResponseDto(shopBasketProductJoin.getProductMarket())
        );
    }

    public ShopRecordResponseDto toShopRecordResponseDto(ShopRecord shopRecord) {
        return DtoMapper.INSTANCE.toShopRecordResponseDto(
                shopRecord,
                toProductMarketResponseDto(
                        shopRecord.getProductMarket()
                )
        );
    }

}
