package com.cleanChoice.cleanChoice.domain.member.application;

import com.cleanChoice.cleanChoice.domain.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;



}
