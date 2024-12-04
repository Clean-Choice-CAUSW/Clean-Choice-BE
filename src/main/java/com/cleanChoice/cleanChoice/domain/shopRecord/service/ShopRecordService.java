package com.cleanChoice.cleanChoice.domain.shopRecord.service;

import com.cleanChoice.cleanChoice.domain.intakeIngredient.domain.IntakeIngredient;
import com.cleanChoice.cleanChoice.domain.intakeIngredient.domain.repository.IntakeIngredientRepository;
import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductIngredientJoin;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductMarket;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductMarketRepository;
import com.cleanChoice.cleanChoice.domain.shopRecord.domain.ShopRecord;
import com.cleanChoice.cleanChoice.domain.shopRecord.dto.response.ShopRecordResponseDto;
import com.cleanChoice.cleanChoice.domain.shopRecord.service.repository.ShopRecordRepository;
import com.cleanChoice.cleanChoice.global.dtoMapper.DtoMapperUtil;
import com.cleanChoice.cleanChoice.global.exceptions.BadRequestException;
import com.cleanChoice.cleanChoice.global.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopRecordService {

    private final ShopRecordRepository shopRecordRepository;
    private final ProductMarketRepository productMarketRepository;

    private final DtoMapperUtil dtoMapperUtil;
    private final IntakeIngredientRepository intakeIngredientRepository;

    @Transactional
    public ShopRecordResponseDto createShopRecord(Member member, Long productMarketId) {
        ProductMarket productMarket= productMarketRepository.findById(productMarketId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

        List<IntakeIngredient> intakeIngredientList = member.getIntakeIngredientList();

        for (ProductIngredientJoin productIngredientJoin : productMarket.getProduct().getProductIngredientJoinList()) {
            IntakeIngredient intakeIngredient = IntakeIngredient.createWithIngredient(
                    member,
                    productIngredientJoin.getIngredient(),
                    productIngredientJoin.getAmountPerServing(),
                    productIngredientJoin.getAmountPerServingUnit()
            );
            intakeIngredientList.add(intakeIngredient);
        }

        intakeIngredientRepository.saveAll(intakeIngredientList);

        return dtoMapperUtil.toShopRecordResponseDto(
                shopRecordRepository.save(
                        ShopRecord.of(
                                member,
                                productMarket
                        )
                ),
                member
        );
    }

    public Page<ShopRecordResponseDto> getShopRecordAllByPage(Member member, Pageable pageable) {
        Page<ShopRecord> shopRecordPage = shopRecordRepository.findAllByMember(member, pageable);

        return shopRecordPage.map(shopRecord -> dtoMapperUtil.toShopRecordResponseDto(shopRecord, member));
    }

    public ShopRecordResponseDto getShopRecord(Member member, Long shopRecordId) {
        ShopRecord shopRecord = shopRecordRepository.findById(shopRecordId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

        if (!shopRecord.getMember().equals(member)) {
            throw new BadRequestException(ErrorCode.API_NOT_ACCESSIBLE);
        }

        return dtoMapperUtil.toShopRecordResponseDto(shopRecord, member);
    }

    @Transactional
    public ShopRecordResponseDto deleteShopRecord(Member member, Long shopRecordId) {
        ShopRecord shopRecord = shopRecordRepository.findById(shopRecordId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

        if (!shopRecord.getMember().equals(member)) {
            throw new BadRequestException(ErrorCode.API_NOT_ACCESSIBLE);
        }

        shopRecordRepository.delete(shopRecord);

        return dtoMapperUtil.toShopRecordResponseDto(shopRecord, member);
    }

    @Transactional
    public void deleteShopRecordAll(Member member) {
        shopRecordRepository.deleteAllByMember(member);
    }

}
