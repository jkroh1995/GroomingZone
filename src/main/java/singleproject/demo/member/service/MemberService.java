package singleproject.demo.member.service;

import org.springframework.stereotype.Service;
import singleproject.demo.member.dto.MemberDto;
import singleproject.demo.member.entity.Member;
import singleproject.demo.member.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void createMember(MemberDto.Post post) {
        Member member = post.postToMember();
        memberRepository.save(member);
    }
}
