package tdd.groomingzone.domain.member.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.domain.member.entity.Member;
import tdd.groomingzone.domain.member.repository.MemberRepository;

@Service
public class MemberCommandService {

    private final MemberRepository memberRepository;

    public MemberCommandService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }
}
