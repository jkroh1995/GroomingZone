package singleproject.demo.member.service;

import org.springframework.stereotype.Service;
import singleproject.demo.exception.BusinessLogicException;
import singleproject.demo.exception.ExceptionCode;
import singleproject.demo.member.Member;
import singleproject.demo.member.dto.MemberDto;
import singleproject.demo.member.repository.MemberRepository;
import singleproject.demo.member.Stamp;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public MemberDto.Response createMember(MemberDto.Post memberPostDto) {
        verifyEmail(memberPostDto.getEmail());
        Member member = initMember(memberPostDto);

        Member savedMember = repository.save(member);

        return savedMember.memberToMemberResponseDto();
    }

    private Member initMember(MemberDto.Post memberPostDto) {
        Member user = memberPostDto.memberPostDtoToMember();
        user.setStamp(new Stamp());
        return user;
    }

    private void verifyEmail(String email) {
        Optional<Member> findUser = repository.findByEmail(email);
        if (findUser.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.EMAIL_ALREADY_EXISTS);
        }
    }

    public MemberDto.Response findMember(long memberId) {
        Optional<Member>optionalMember = repository.findByMemberId(memberId);
        Member member = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUNT));
        return member.memberToMemberResponseDto();
    }

    public Member getMember(long memberId) {
        Optional<Member>optionalMember = repository.findByMemberId(memberId);
        return optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUNT));
    }
}
