package com.cleanChoice.cleanChoice.domain.shopBasket.service;

import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.shopBasket.domain.repository.ShopBasketProductJoinRepository;
import com.cleanChoice.cleanChoice.domain.shopBasket.domain.repository.ShopBasketRepository;
import com.cleanChoice.cleanChoice.domain.shopBasket.dto.response.ShopBasketProductJoinResponseDto;
import com.cleanChoice.cleanChoice.domain.shopBasket.dto.response.ShopBasketResponseDto;
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

    @Transactional
    public ShopBasketResponseDto createShopBasket(Member member, String basketName) {
        return null;
    }

    @Transactional
    public ShopBasketProductJoinResponseDto addProduct(Member member, Long productMarketId) {
        return null;
    }

    public ShopBasketResponseDto getShopBasket(Member member, Long shopBasketId) {
        return null;
    }

    public ShopBasketProductJoinResponseDto getShopBasketProduct(Member member, Long shopBasketProductJoinId) {
        return null;
    }

    @Transactional
    public ShopBasketProductJoinResponseDto deleteProduct(Member member, Long shopBasketProductJoinId) {
        return null;
    }

    @Transactional
    public List<ShopBasketProductJoinResponseDto> deleteAllProduct(Member member) {
        return null;
    }

    @Transactional
    public ShopBasketResponseDto deleteShopBasket(Member member, Long shopBasketId) {
        return null;
    }

    public List<ShopBasketResponseDto> getShopBasketList(Member member) {
        return null;
    }

}
