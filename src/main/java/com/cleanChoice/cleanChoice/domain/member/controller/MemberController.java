package com.cleanChoice.cleanChoice.domain.member.controller;

import com.cleanChoice.cleanChoice.domain.member.service.MemberService;
import com.cleanChoice.cleanChoice.domain.member.dto.response.JwtTokenResponseDto;
import com.cleanChoice.cleanChoice.domain.member.dto.request.SignInRequestDto;
import com.cleanChoice.cleanChoice.domain.member.dto.request.SignUpRequestDto;
import com.cleanChoice.cleanChoice.domain.member.dto.request.SignoutRequestDto;
import com.cleanChoice.cleanChoice.domain.member.dto.request.UpdateMemberRequestDto;
import com.cleanChoice.cleanChoice.domain.member.dto.response.MemberResponseDto;
import com.cleanChoice.cleanChoice.global.config.security.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회원가입", description = "회원가입을 합니다.")
    public MemberResponseDto signUp(
            @RequestBody @Valid SignUpRequestDto signUpRequestDto
    ) {
        return memberService.signUp(signUpRequestDto);
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "로그인", description = "로그인을 합니다.")
    public JwtTokenResponseDto signIn(
            @RequestBody @Valid SignInRequestDto signInRequestDto
    ) {
        return memberService.signIn(signInRequestDto);
    }

    @PostMapping("/sign-out")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "로그아웃", description = "로그아웃을 합니다.")
    public void signOut(
            @RequestBody @Valid SignoutRequestDto signOutRequestDto
    ) {
        memberService.signOut(signOutRequestDto);
    }

    @PostMapping("/update-token")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "토큰 갱신", description = "토큰을 갱신합니다.")
    public JwtTokenResponseDto updateToken(
            @RequestBody @Valid String refreshToken
    ) {
        return memberService.updateToken(refreshToken);
    }

    @GetMapping("/duplicate_check")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "아이디 중복 확인", description = "아이디 중복을 확인합니다.")
    public boolean isDuplicatedLoginId(
            @RequestBody String loginId
    ) {
        return memberService.isDuplicateLoginId(loginId);
    }

    @DeleteMapping("/delete-self")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 합니다.")
    public void deleteMember(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        memberService.deleteMember(customUserDetails.getMember());
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "현재 로그인 회원 조회", description = "현재 로그인한 회원을 조회합니다.")
    public MemberResponseDto getCurrentMember(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return memberService.getCurrentMember(customUserDetails.getMember());
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다.")
    public MemberResponseDto updateMember(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid UpdateMemberRequestDto updateMemberRequestDto
    ) {
        return memberService.updateMember(customUserDetails.getMember(), updateMemberRequestDto);
    }

    @GetMapping("/advice")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "유저 정보 기반 조언 조회(question null로 보낼 시 유저 기본 정보 토대로 조언, 있을 시, 질문 기반 답변)", description = "추천 정보를 조회합니다.")
    public String getAdvice(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody(required = false) String question
    ) {
        return memberService.getAdvice(customUserDetails.getMember(), question);
    }

}
