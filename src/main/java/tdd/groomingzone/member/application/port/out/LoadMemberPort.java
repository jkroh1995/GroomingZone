package tdd.groomingzone.member.application.port.out;

import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;
import tdd.groomingzone.member.domain.Member;

import java.util.Optional;

public interface LoadMemberPort {

    Member findMemberById(long memberId);

    Optional<MemberEntity> findOptionalMemberByEmail(String email);
}
