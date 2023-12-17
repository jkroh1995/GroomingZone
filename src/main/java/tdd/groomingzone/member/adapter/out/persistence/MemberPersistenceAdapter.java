package tdd.groomingzone.member.adapter.out.persistence;

import org.springframework.stereotype.Repository;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.member.application.port.LoadMemberPort;
import tdd.groomingzone.member.application.port.SaveMemberPort;
import tdd.groomingzone.member.domain.Member;

import java.util.Optional;

@Repository
public class MemberPersistenceAdapter implements LoadMemberPort, SaveMemberPort {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberPersistenceAdapter(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @Override
    public Member save(Member member) {
        MemberEntity memberEntity = memberMapper.mapToDatabaseEntity(member);
        MemberEntity savedMemberEntity = memberRepository.save(memberEntity);
        return memberMapper.mapToDomainEntity(savedMemberEntity);
    }

    @Override
    public Member findMemberById(long memberId) {
        MemberEntity findMember = findMemberByIdOrElseThrowException(memberId);
        return memberMapper.mapToDomainEntity(findMember);
    }

    @Override
    public Optional<MemberEntity> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    private MemberEntity findMEmberByEmailOrElseThrowException(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() ->
                new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    private MemberEntity findMemberByIdOrElseThrowException(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() ->
                new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
