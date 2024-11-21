package com.cleanChoice.cleanChoice.global.config.security.userDetails;

import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.member.domain.repository.MemberRepository;
import com.cleanChoice.cleanChoice.global.exceptions.BadRequestException;
import com.cleanChoice.cleanChoice.global.exceptions.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return CustomUserDetails.builder()
                .member(member)
                .build();
    }

    private UserDetails createUserDetails(Member member) {
        return User.builder()
                .username(member.getLoginId())
                .password(member.getPassword())
                .build();
    }

    public UserDetails loadUserByUserId(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST, "User not found"));

        return CustomUserDetails.builder()
                .member(member)
                .build();
    }

}
