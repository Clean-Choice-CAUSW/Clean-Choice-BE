package com.cleanChoice.cleanChoice.global.dtoMapper;

import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.member.dto.response.MemberResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberDtoMapper {

    MemberDtoMapper INSTANCE = Mappers.getMapper(MemberDtoMapper.class);

    MemberResponseDto toMemberResponseDto(Member member);

}
