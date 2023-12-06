package tdd.groomingzone.domain.member.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.domain.member.entity.Member;
import tdd.groomingzone.domain.member.dto.MemberDto;

import java.util.List;

@Service
public class MemberConverter {
    public Member convertPostDtoToEntity(MemberDto.Post dto, List<String> roles) {
        return Member.builder()
                .email(dto.email)
                .password(dto.password)
                .name(dto.name)
                .phoneNumber(dto.phoneNumber)
                .roles(roles)
                .build();
    }

    public MemberDto.Response convertEntityToResponseDto(Member member) {
        return MemberDto.Response
                .builder()
                .email(member.getEmail())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .role(member.getRoles().get(0))
                .build();
    }
}
