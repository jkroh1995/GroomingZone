package tdd.groomingzone.member.application.port;

import tdd.groomingzone.member.domain.Member;

public interface SaveMemberPort {

    Member save(Member member);
}
