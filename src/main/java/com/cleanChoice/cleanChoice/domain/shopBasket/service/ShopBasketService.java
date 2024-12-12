package com.cleanChoice.cleanChoice.domain.shopBasket.service;

import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductMarket;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductMarketRepository;
import com.cleanChoice.cleanChoice.domain.shopBasket.domain.ShopBasket;
import com.cleanChoice.cleanChoice.domain.shopBasket.domain.ShopBasketProductJoin;
import com.cleanChoice.cleanChoice.domain.shopBasket.domain.repository.ShopBasketProductJoinRepository;
import com.cleanChoice.cleanChoice.domain.shopBasket.domain.repository.ShopBasketRepository;
import com.cleanChoice.cleanChoice.domain.shopBasket.dto.response.ShopBasketProductJoinResponseDto;
import com.cleanChoice.cleanChoice.domain.shopBasket.dto.response.ShopBasketResponseDto;
import com.cleanChoice.cleanChoice.global.dtoMapper.DtoMapperUtil;
import com.cleanChoice.cleanChoice.global.exceptions.BadRequestException;
import com.cleanChoice.cleanChoice.global.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopBasketService {

    private final ShopBasketRepository shopBasketRepository;
    private final ShopBasketProductJoinRepository shopBasketProductJoinRepository;

    private final DtoMapperUtil dtoMapperUtil;
    private final ProductMarketRepository productMarketRepository;

    @Transactional
    public ShopBasketResponseDto createShopBasket(Member member, String basketName) {
        return dtoMapperUtil.toShopBasketResponseDto(
                shopBasketRepository.save(ShopBasket.of(member, basketName)),
                member
        );
    }

    @Transactional
    public ShopBasketProductJoinResponseDto addProduct(Member member, Long shopBasketId, Long productMarketId) {
        ShopBasket shopBasket = shopBasketRepository.findById(shopBasketId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

        if (!shopBasket.getMember().getId().equals(member.getId())) {
            throw new BadRequestException(ErrorCode.API_NOT_ACCESSIBLE);
        }

        ProductMarket productMarket = productMarketRepository.findById(productMarketId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

        ShopBasketProductJoin shopBasketProductJoin = ShopBasketProductJoin.of(shopBasket, productMarket);

        return dtoMapperUtil.toShopBasketProductJoinResponseDto(
                shopBasketProductJoinRepository.save(shopBasketProductJoin),
                member
        );
    }

    public ShopBasketResponseDto getShopBasket(Member member, Long shopBasketId) {
        ShopBasket shopBasket = shopBasketRepository.findById(shopBasketId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

        if (!shopBasket.getMember().getId().equals(member.getId())) {
            throw new BadRequestException(ErrorCode.API_NOT_ACCESSIBLE);
        }

        return dtoMapperUtil.toShopBasketResponseDto(shopBasket, member);
    }

    public ShopBasketProductJoinResponseDto getShopBasketProduct(Member member, Long shopBasketProductJoinId) {
        ShopBasketProductJoin shopBasketProductJoin = shopBasketProductJoinRepository.findById(shopBasketProductJoinId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

        if (!shopBasketProductJoin.getShopBasket().getMember().getId().equals(member.getId())) {
            throw new BadRequestException(ErrorCode.API_NOT_ACCESSIBLE);
        }

        return dtoMapperUtil.toShopBasketProductJoinResponseDto(shopBasketProductJoin, member);
    }

    @Transactional
    public ShopBasketProductJoinResponseDto deleteProduct(Member member, Long shopBasketProductJoinId) {
        ShopBasketProductJoin shopBasketProductJoin = shopBasketProductJoinRepository.findById(shopBasketProductJoinId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

        if (!shopBasketProductJoin.getShopBasket().getMember().getId().equals(member.getId())) {
            throw new BadRequestException(ErrorCode.API_NOT_ACCESSIBLE);
        }

        shopBasketProductJoinRepository.delete(shopBasketProductJoin);

        return dtoMapperUtil.toShopBasketProductJoinResponseDto(shopBasketProductJoin, member);
    }

    @Transactional
    public List<ShopBasketProductJoinResponseDto> deleteAllProduct(Member member, Long shopBasketId) {
        ShopBasket shopBasket = shopBasketRepository.findById(shopBasketId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

        if (!shopBasket.getMember().equals(member)) {
            throw new BadRequestException(ErrorCode.API_NOT_ACCESSIBLE);
        }

        List<ShopBasketProductJoin> shopBasketProductJoinList = shopBasket.getShopBasketProductJoinList();

        shopBasketProductJoinRepository.deleteAll(shopBasketProductJoinList);

        return shopBasketProductJoinList.stream()
                .map(shopBasketProductJoin -> dtoMapperUtil.toShopBasketProductJoinResponseDto(shopBasketProductJoin, member))
                .toList();
    }

    @Transactional
    public ShopBasketResponseDto deleteShopBasket(Member member, Long shopBasketId) {
        ShopBasket shopBasket = shopBasketRepository.findById(shopBasketId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

        if (!shopBasket.getMember().getId().equals(member.getId())) {
            throw new BadRequestException(ErrorCode.API_NOT_ACCESSIBLE);
        }

        shopBasketRepository.delete(shopBasket);

        return dtoMapperUtil.toShopBasketResponseDto(shopBasket, member);
    }

    public List<ShopBasketResponseDto> getShopBasketList(Member member) {
        List<ShopBasket> shopBasketList = shopBasketRepository.findAllByMember(member);

        return shopBasketList.stream()
                .map(shopBasket -> dtoMapperUtil.toShopBasketResponseDto(shopBasket, member))
                .toList();
    }

}
