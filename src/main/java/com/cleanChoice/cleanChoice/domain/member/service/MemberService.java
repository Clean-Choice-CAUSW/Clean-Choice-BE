package com.cleanChoice.cleanChoice.domain.member.service;

import com.cleanChoice.cleanChoice.domain.ingredient.domain.Ingredient;
import com.cleanChoice.cleanChoice.domain.intakeIngredient.domain.IntakeIngredient;
import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.member.domain.repository.MemberRepository;
import com.cleanChoice.cleanChoice.domain.member.dto.request.*;
import com.cleanChoice.cleanChoice.domain.member.dto.response.JwtTokenResponseDto;
import com.cleanChoice.cleanChoice.domain.member.dto.response.MemberResponseDto;
import com.cleanChoice.cleanChoice.domain.openAi.service.OpenAiService;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductIngredientJoin;
import com.cleanChoice.cleanChoice.domain.product.domain.ProductMarket;
import com.cleanChoice.cleanChoice.domain.product.domain.repository.ProductMarketRepository;
import com.cleanChoice.cleanChoice.global.config.jwt.JwtTokenProvider;
import com.cleanChoice.cleanChoice.global.config.redis.RedisUtils;
import com.cleanChoice.cleanChoice.global.dtoMapper.DtoMapper;
import com.cleanChoice.cleanChoice.global.exceptions.BadRequestException;
import com.cleanChoice.cleanChoice.global.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtils redisUtils;
    private final JwtTokenProvider jwtTokenProvider;
    private final OpenAiService openAiService;
    private final ProductMarketRepository productMarketRepository;

    @Transactional
    public MemberResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        if (memberRepository.existsByLoginId(signUpRequestDto.getLoginId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        Member member = Member.from(
                signUpRequestDto,
                passwordEncoder.encode(signUpRequestDto.getPassword())
        );

        memberRepository.save(member);

        return this.toResponseDto(member);
    }

    public JwtTokenResponseDto signIn(SignInRequestDto signInRequestDto) {
        Member member = memberRepository.findByLoginId(signInRequestDto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                member.getLoginId(),
                member.getPassword()
        );

        if (!passwordEncoder.matches(signInRequestDto.getPassword(), member.getPassword())) {
            throw new BadRequestException(
                    ErrorCode.INVALID_SIGNIN,
                    "비밀번호가 일치하지 않습니다."
            );
        }

        JwtTokenResponseDto jwtTokenResponseDto = jwtTokenProvider.generateToken(member.getId());

        redisUtils.setRefreshTokenData(jwtTokenResponseDto.getRefreshToken(), member.getId());

        return jwtTokenResponseDto;
    }

    public JwtTokenResponseDto updateToken(String refreshToken) {
        Member member = memberRepository.findById(getMemberIdFromRefreshToken(refreshToken))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                member.getLoginId(),
                null
        );

        JwtTokenResponseDto newJwtTokenResponseDto = jwtTokenProvider.generateToken(member.getId());

        redisUtils.setRefreshTokenData(newJwtTokenResponseDto.getRefreshToken(), member.getId());

        redisUtils.deleteRefreshTokenData(refreshToken);

        return newJwtTokenResponseDto;
    }

    public void signOut(SignoutRequestDto signoutRequestDto) {
        if (redisUtils.isTokenBlacklisted(signoutRequestDto.getAccessToken())) {
            throw new IllegalArgumentException("이미 로그아웃된 토큰입니다.");
        }

        redisUtils.addToBlacklist(signoutRequestDto.getAccessToken());
        redisUtils.deleteRefreshTokenData(signoutRequestDto.getRefreshToken());
    }

    public Boolean isDuplicateLoginId(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    @Transactional
    public void deleteMember(Member member) {
        member = memberRepository.findById(member.getId())
                .orElseThrow(() -> new BadRequestException(
                        ErrorCode.ROW_DOES_NOT_EXIST,
                        "해당하는 사용자가 없습니다."
                ));
        memberRepository.delete(member);
    }

    public MemberResponseDto getCurrentMember(Member member) {
        return this.toResponseDto(member);
    }

    @Transactional
    public MemberResponseDto updateMember(Member member, UpdateMemberRequestDto updateMemberRequestDto) {
        member.updateMember(
                updateMemberRequestDto.getPassword() == null ? member.getPassword() : passwordEncoder.encode(updateMemberRequestDto.getPassword()),
                updateMemberRequestDto.getName() == null ? member.getName() : updateMemberRequestDto.getName(),
                updateMemberRequestDto.getAge() == null ? member.getAge() : updateMemberRequestDto.getAge(),
                updateMemberRequestDto.getGender() == null ? member.getGender() : updateMemberRequestDto.getGender(),
                updateMemberRequestDto.getIsPregnant() == null ? member.getIsPregnant() : updateMemberRequestDto.getIsPregnant()
        );

        return toResponseDto(memberRepository.save(member));
    }

    public String getAdvice(Member member, GetAdviceRequestDto getAdviceRequestDto) {
        member = memberRepository.findById(member.getId()).orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

        List<String> intakeIngredientNameList = getIntakeIngredientNameList(member);

        String intakeIngredientListString = null;
        if (!intakeIngredientNameList.isEmpty()) {
            intakeIngredientListString = intakeIngredientNameList.stream().toList().toString();
        }

        ProductMarket productMarket = productMarketRepository.findById(getAdviceRequestDto.getProductMarketId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.ROW_DOES_NOT_EXIST));

        List<String> selectIngredientNameString = productMarket.getProduct().getProductIngredientJoinList()
                .stream().map(ProductIngredientJoin::getIngredient)
                .toList()
                .stream().map(Ingredient::getEnglishName)
                .toList();

        String selectIngredientListString = null;

        if (!selectIngredientNameString.isEmpty()) {
            selectIngredientListString = selectIngredientNameString.stream().toList().toString();
        }

        return openAiService.getAdvice(
                member.getAge(),
                member.getGender(),
                member.getIsPregnant(),
                intakeIngredientListString,
                selectIngredientListString,
                getAdviceRequestDto.getQuestion()
        );
    }

    private List<String> getIntakeIngredientNameList(Member member) {
        List<IntakeIngredient> intakeIngredientList = member.getIntakeIngredientList();

        List<String> intakeIngredientNameList = new ArrayList<>();

        for (IntakeIngredient intakeIngredient : intakeIngredientList) {
            if (intakeIngredient.getFakeName() != null) {
                intakeIngredientNameList.add(intakeIngredient.getFakeName());
            } else {
                intakeIngredientNameList.add(intakeIngredient.getIngredient().getEnglishName());
            }
        }
        return intakeIngredientNameList;
    }

    // private method

    private Long getMemberIdFromRefreshToken(String refreshToken) {
        return Optional.ofNullable(redisUtils.getRefreshTokenData(refreshToken))
                .orElseThrow(() -> new BadRequestException(
                        ErrorCode.ROW_DOES_NOT_EXIST,
                        "해당하는 refreshToken에 맵핑된 사용자가 없습니다."
                ));
    }

    private MemberResponseDto toResponseDto(Member member) {
        return DtoMapper.INSTANCE.toMemberResponseDto(member);
    }

}
