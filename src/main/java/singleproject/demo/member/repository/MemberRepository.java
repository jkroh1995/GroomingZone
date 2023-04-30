package singleproject.demo.member.repository;

import org.springframework.data.repository.Repository;
import singleproject.demo.member.Member;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {

    Member save(Member member);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByMemberId(long memberId);
}
