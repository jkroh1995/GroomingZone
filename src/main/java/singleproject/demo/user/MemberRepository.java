package singleproject.demo.user;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {

    void save(Member member);

    Optional<Member> findByEmail(String email);
}
