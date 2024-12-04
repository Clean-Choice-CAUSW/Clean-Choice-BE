package com.cleanChoice.cleanChoice.domain.product.domain.repository;

import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.product.domain.PersonalizedInfo;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalizedInfoRepository extends JpaRepository<PersonalizedInfo, Long> {

    List<PersonalizedInfo> findByProductMarketAndMember(ProductMarket productMarket, Member member);
}
