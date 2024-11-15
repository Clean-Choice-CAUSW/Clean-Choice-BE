package com.cleanChoice.cleanChoice.domain.member.application;

import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.member.domain.repository.MemberRepository;
import com.cleanChoice.cleanChoice.domain.member.dto.request.SignUpRequestDto;
import com.cleanChoice.cleanChoice.domain.member.dto.response.MemberResponseDto;
import com.cleanChoice.cleanChoice.global.dtoMapper.MemberDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        if (memberRepository.existsByLoginId(signUpRequestDto.getLoginId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        Member member = Member.of(
                signUpRequestDto.getLoginId(),
                passwordEncoder.encode(signUpRequestDto.getPassword()),
                signUpRequestDto.getName()
        );

        memberRepository.save(member);

        return this.toResponseDto(member);
    }

    private MemberResponseDto toResponseDto(Member member) {
        return MemberDtoMapper.INSTANCE.toMemberResponseDto(member);
    }
}
