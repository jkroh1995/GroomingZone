package tdd.groomingzone.member.adapter.out.persistence;

import org.springframework.stereotype.Repository;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.member.adapter.out.persistence.repository.MemberEntitiyRepository;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.application.port.out.SaveMemberPort;
import tdd.groomingzone.member.domain.Member;

import java.util.Optional;

@Repository
public class MemberPersistenceAdapter implements LoadMemberPort, SaveMemberPort {

    private final MemberEntitiyRepository memberEntitiyRepository;
    private final MemberMapper memberMapper;

    public MemberPersistenceAdapter(MemberEntitiyRepository memberEntitiyRepository, MemberMapper memberMapper) {
        this.memberEntitiyRepository = memberEntitiyRepository;
        this.memberMapper = memberMapper;
    }

    @Override
    public Member save(Member member) {
        MemberEntity memberEntity = memberMapper.mapToDatabaseEntity(member);
        MemberEntity savedMemberEntity = memberEntitiyRepository.save(memberEntity);
        return memberMapper.mapToDomainEntity(savedMemberEntity);
    }

    @Override
    public Member findMemberById(long memberId) {
        MemberEntity findMember = memberEntitiyRepository.findById(memberId).orElseThrow(() ->
                new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        return memberMapper.mapToDomainEntity(findMember);
    }

    @Override
    public Optional<MemberEntity> findOptionalMemberByEmail(String email) {
        return memberEntitiyRepository.findByEmail(email);
    }

    @Override
    public Member findMemberByEmail(String email) {
        MemberEntity findMember = findOptionalMemberByEmail(email).orElseThrow(() ->
                new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        return memberMapper.mapToDomainEntity(findMember);
    }
}
