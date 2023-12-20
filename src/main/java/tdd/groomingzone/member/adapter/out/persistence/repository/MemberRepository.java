package tdd.groomingzone.member.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(String email);
}
