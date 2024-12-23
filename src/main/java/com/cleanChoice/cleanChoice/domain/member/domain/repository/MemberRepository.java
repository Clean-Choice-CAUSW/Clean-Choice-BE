package com.cleanChoice.cleanChoice.domain.member.domain.repository;

import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    Boolean existsByLoginId(String loginId);

}
