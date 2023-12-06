package tdd.groomingzone.domain.member;

import tdd.groomingzone.domain.member.dto.MemberDto;

public interface MemberService {
    MemberDto.Response postMember(MemberDto.Post dto);
}
