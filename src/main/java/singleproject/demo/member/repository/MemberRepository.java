package singleproject.demo.member.repository;

import org.springframework.data.repository.Repository;
import singleproject.demo.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);

    Optional<Member> findByNickname(String nickname);
}
