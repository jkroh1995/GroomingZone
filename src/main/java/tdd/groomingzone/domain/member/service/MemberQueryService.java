package tdd.groomingzone.domain.member.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.domain.member.entity.Member;
import tdd.groomingzone.domain.member.repository.MemberRepository;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

@Service
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public MemberQueryService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member readMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() ->
                new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
