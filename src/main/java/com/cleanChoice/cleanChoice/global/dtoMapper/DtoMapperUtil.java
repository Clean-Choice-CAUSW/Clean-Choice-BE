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
import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.product.domain.*;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.PersonalizedInfoRepository;
import com.cleanChoice.cleanChoice.domain.product.dto.response.*;
import com.cleanChoice.cleanChoice.domain.shopBasket.domain.ShopBasket;
import com.cleanChoice.cleanChoice.domain.shopBasket.domain.ShopBasketProductJoin;
import com.cleanChoice.cleanChoice.domain.shopBasket.dto.response.ShopBasketProductJoinResponseDto;
import com.cleanChoice.cleanChoice.domain.shopBasket.dto.response.ShopBasketResponseDto;
import com.cleanChoice.cleanChoice.domain.shopRecord.domain.ShopRecord;
import com.cleanChoice.cleanChoice.domain.shopRecord.dto.response.ShopRecordResponseDto;
import com.cleanChoice.cleanChoice.domain.viewRecord.domain.ViewRecord;
import com.cleanChoice.cleanChoice.domain.viewRecord.dto.response.ViewRecordResponseDto;
import com.cleanChoice.cleanChoice.global.exceptions.ErrorCode;
import com.cleanChoice.cleanChoice.global.exceptions.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DtoMapperUtil {

    private final CombineUseBanedIngredientRepository combineUseBanedIngredientRepository;
    private final PersonalizedInfoRepository personalizedInfoRepository;

    public AnalyzeResponseDto toAnalyzeResponseDto(ProductMarket productMarket, AnalyzeType analyzeType, Member member, String imageUrl) {
        return DtoMapper.INSTANCE.toAnalyzeResponseDto(
                productMarket,
                toProductResponseDto(productMarket.getProduct(), member),
                analyzeType,
                imageUrl
        );
    }

    public ProductMarketResponseDto toProductMarketResponseDto(ProductMarket productMarket, Member member) {
        List<PersonalizedInfo> personalizedInfoList = personalizedInfoRepository.findByProductMarketAndMember(productMarket, member);

        Product fakeProduct = productMarket.getProduct();

        if (personalizedInfoList.size() > 1) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER);
        } else if (personalizedInfoList.size() == 1) {
            PersonalizedInfo personalizedInfo = personalizedInfoList.get(0);

            fakeProduct.maskWithPersonalizedInfo(personalizedInfo);
            productMarket.maskWithPersonalizedInfo(personalizedInfo, fakeProduct);
        }

        return DtoMapper.INSTANCE.toProductMarketResponseDto(
                productMarket,
                toProductResponseDto(productMarket.getProduct(), member)
        );
    }

    public ProductResponseDto toProductResponseDto(Product product, Member member) {
        List<ProductIngredientJoinResponseDto> productIngredientJoinResponseDtoList = new ArrayList<>();
        List<ProductLabelStatementResponseDto> productLabelStatementResponseDtoList = new ArrayList<>();

        if (!(product.getProductIngredientJoinList() == null || product.getProductIngredientJoinList().isEmpty())) {
            for (ProductIngredientJoin productIngredientJoin : product.getProductIngredientJoinList()) {
                productIngredientJoinResponseDtoList.add(toProductIngredientJoinResponseDto(productIngredientJoin));
            }
        }

        if (!(product.getProductLabelStatementList() == null || product.getProductLabelStatementList().isEmpty())) {
            for (ProductLabelStatement productLabelStatement : product.getProductLabelStatementList()) {
                productLabelStatementResponseDtoList.add(toProductLabelStatementResponseDto(productLabelStatement));
            }
        }

        return DtoMapper.INSTANCE.toProductResponseDto(
                product,
                productIngredientJoinResponseDtoList,
                productLabelStatementResponseDtoList
        );
    }

    public ProductIngredientJoinResponseDto toProductIngredientJoinResponseDto(ProductIngredientJoin productIngredientJoin) {
        System.out.println(productIngredientJoin.getIngredient() == null);
        IngredientResponseDto ingredientResponseDto = toIngredientResponseDto(productIngredientJoin.getIngredient());
        System.out.println(ingredientResponseDto == null);
        return DtoMapper.INSTANCE.toProductIngredientJoinResponseDto(
                productIngredientJoin,
                ingredientResponseDto
        );
    }

    public IngredientResponseDto toIngredientResponseDto(Ingredient ingredient) {
        List<BanedIngredientInfoResponseDto> banedIngredientInfoResponseDtoList = new ArrayList<>();
        List<CombineUseBanedIngredientInfoResponseDto> combineUseBanedIngredientInfoResponseDtoList = new ArrayList<>();

        if (!(ingredient.getBanedIngredientInfoList() == null || ingredient.getBanedIngredientInfoList().isEmpty())) {
            for (BanedIngredientInfo banedIngredientInfo : ingredient.getBanedIngredientInfoList()) {
                banedIngredientInfoResponseDtoList.add(toBanedIngredientInfoResponseDto(banedIngredientInfo));
            }
        }

        List<CombineUseBanedIngredientInfo> combineUseBanedIngredientInfoList = combineUseBanedIngredientRepository.findAllByIngredientAndCombineIngredient(ingredient.getId());

        if (!(combineUseBanedIngredientInfoList == null || combineUseBanedIngredientInfoList.isEmpty())) {
            for (CombineUseBanedIngredientInfo combineUseBanedIngredientInfo : combineUseBanedIngredientInfoList) {
                combineUseBanedIngredientInfoResponseDtoList.add(toCombineUseBanedIngredientInfoResponseDto(combineUseBanedIngredientInfo));
            }
        }

        return DtoMapper.INSTANCE.toIngredientResponseDto(
                ingredient,
                banedIngredientInfoResponseDtoList,
                combineUseBanedIngredientInfoResponseDtoList
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

    public ShopBasketResponseDto toShopBasketResponseDto(ShopBasket shopBasket, Member member) {
        List<ShopBasketProductJoin> shopBasketProductJoinList = shopBasket.getShopBasketProductJoinList();
        List<ShopBasketProductJoinResponseDto> shopBasketProductJoinResponseDtoList = new ArrayList<>();
        if (!(shopBasketProductJoinList == null || shopBasketProductJoinList.isEmpty())) {
            for (ShopBasketProductJoin shopBasketProductJoin : shopBasketProductJoinList) {
                shopBasketProductJoinResponseDtoList.add(toShopBasketProductJoinResponseDto(shopBasketProductJoin, member));
            }
        }

        return DtoMapper.INSTANCE.toShopBasketResponseDto(
                shopBasket,
                shopBasketProductJoinResponseDtoList
        );
    }

    public ShopBasketProductJoinResponseDto toShopBasketProductJoinResponseDto(ShopBasketProductJoin shopBasketProductJoin, Member member) {
        return DtoMapper.INSTANCE.toShopBasketProductJoinResponseDto(
                shopBasketProductJoin,
                toProductMarketResponseDto(shopBasketProductJoin.getProductMarket(), member)
        );
    }

    public ShopRecordResponseDto toShopRecordResponseDto(ShopRecord shopRecord, Member member) {
        return DtoMapper.INSTANCE.toShopRecordResponseDto(
                shopRecord,
                toProductMarketResponseDto(
                        shopRecord.getProductMarket(),
                        member
                )
        );
    }

    public ViewRecordResponseDto toViewRecordResponseDto(ViewRecord viewRecord, Member member) {
        return DtoMapper.INSTANCE.toViewRecordResponseDto(
                viewRecord,
                toProductMarketResponseDto(
                        viewRecord.getProductMarket(),
                        member
                )
        );
    }

}
