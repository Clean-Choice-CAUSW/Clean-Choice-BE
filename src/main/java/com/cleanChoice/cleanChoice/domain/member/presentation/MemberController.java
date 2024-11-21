package com.cleanChoice.cleanChoice.domain.member.presentation;

import com.cleanChoice.cleanChoice.domain.member.application.MemberService;
import com.cleanChoice.cleanChoice.domain.member.dto.JwtToken;
import com.cleanChoice.cleanChoice.domain.member.dto.request.SignInRequestDto;
import com.cleanChoice.cleanChoice.domain.member.dto.request.SignUpRequestDto;
import com.cleanChoice.cleanChoice.domain.member.dto.request.SignoutRequestDto;
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
    public JwtToken signIn(
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
    public JwtToken updateToken(
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

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "테스트", description = "테스트를 합니다.")
    public MemberResponseDto test(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return memberService.getCurrentMember(customUserDetails.getMember());
    }

}
