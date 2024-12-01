package com.cleanChoice.cleanChoice.global.dtoMapper;

import com.cleanChoice.cleanChoice.domain.home.dto.response.AnalyzeResponseDto;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.BanType;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.BanedIngredientInfo;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.CombineUseBanedIngredientInfo;
import com.cleanChoice.cleanChoice.domain.ingredient.domain.Ingredient;
import com.cleanChoice.cleanChoice.domain.ingredient.dto.response.BanedIngredientInfoResponseDto;
import com.cleanChoice.cleanChoice.domain.ingredient.dto.response.CombineUseBanedIngredientInfoResponseDto;
import com.cleanChoice.cleanChoice.domain.ingredient.dto.response.IngredientResponseDto;
import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.member.dto.response.MemberResponseDto;
import com.cleanChoice.cleanChoice.domain.product.domain.*;
import com.cleanChoice.cleanChoice.domain.product.dto.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
@Mapping(target = "id", source = "entity.id")
@Mapping(target = "createdAt", source = "entity.createdAt")
@Mapping(target = "updatedAt", source = "entity.updatedAt")
@interface CommonEntityMappings {
}

@Mapper(componentModel = "spring")
public interface DtoMapper {

    DtoMapper INSTANCE = Mappers.getMapper(DtoMapper.class);

    @Named("statementTypeStringValue")
    default String statementTypeStringValue(StatementType statementType) {
        if (statementType == null) return null;
        return statementType.getValue();
    }

    @Named("banTypeDescription")
    default String banTypeDescription(BanType banType) {
        if (banType == null) return null;
        return banType.getDescription();
    }

    // Member

    MemberResponseDto toMemberResponseDto(Member member);


    // Product Market

    @CommonEntityMappings
    @Mapping(target = "productResponseDto", source = "productResponseDto")
    ProductMarketResponseDto toProductMarketResponseDto(ProductMarket entity, ProductResponseDto productResponseDto);

    @CommonEntityMappings
    @Mapping(target = "productResponseDto", source = "productResponseDto")
    @Mapping(target = "analyzeType", source = "analyzeType")
    AnalyzeResponseDto toAnalyzeResponseDto(ProductMarket entity, ProductResponseDto productResponseDto, AnalyzeType analyzeType);


    // Product

    @CommonEntityMappings
    @Mapping(target = "productIngredientJoinResponseDtoList", source = "productIngredientJoinResponseDtoList")
    @Mapping(target = "productLabelStatementResponseDtoList", source = "productLabelStatementResponseDtoList")
    ProductResponseDto toProductResponseDto(
            Product entity,
            List<ProductIngredientJoinResponseDto> productIngredientJoinResponseDtoList,
            List<ProductLabelStatementResponseDto> productLabelStatementResponseDtoList
    );

    @CommonEntityMappings
    @Mapping(target = "ingredientResponseDto", source = "ingredientResponseDto")
    @Mapping(target = "productId", source = "entity.product.id")
    ProductIngredientJoinResponseDto toProductIngredientJoinResponseDto(
            ProductIngredientJoin entity,
            IngredientResponseDto ingredientResponseDto
    );

    @CommonEntityMappings
    @Mapping(target = "statementTypeStringValue", source = "statementType", qualifiedByName = "statementTypeStringValue")
    ProductLabelStatementResponseDto toProductLabelStatementResponseDto(ProductLabelStatement entity);


    // Ingredient

    @CommonEntityMappings
    @Mapping(target = "banedIngredientInfoResponseDtoList", source = "banedIngredientInfoResponseDtoList")
    @Mapping(target = "combineUseBanedIngredientInfoResponseDtoList", source = "combineUseBanedIngredientInfoResponseDtoList")
    IngredientResponseDto toIngredientResponseDto(
            Ingredient entity,
            List<BanedIngredientInfoResponseDto> banedIngredientInfoResponseDtoList,
            List<CombineUseBanedIngredientInfoResponseDto> combineUseBanedIngredientInfoResponseDtoList
    );

    @CommonEntityMappings
    @Mapping(target = "ingredientId", source = "entity.ingredient.id")
    @Mapping(target = "banTypeDescription", source = "entity.banType", qualifiedByName = "banTypeDescription")
    BanedIngredientInfoResponseDto toBanedIngredientInfoResponseDto(BanedIngredientInfo entity);

    @CommonEntityMappings
    @Mapping(target = "ingredientId", source = "entity.ingredient.id")
    @Mapping(target = "ingredientEnglishName", source = "entity.ingredient.englishName")
    @Mapping(target = "ingredientKoreanName", source = "entity.ingredient.koreanName")
    @Mapping(target = "combineIngredientEnglishName", source = "entity.ingredient.englishName")
    @Mapping(target = "combineIngredientKoreanName", source = "entity.ingredient.koreanName")
    CombineUseBanedIngredientInfoResponseDto toCombineUseBanedIngredientInfoResponseDto(
            CombineUseBanedIngredientInfo entity
    );

}
