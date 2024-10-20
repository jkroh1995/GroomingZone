package tdd.groomingzone.post.freeboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBoardEntityRepository extends JpaRepository<FreeBoardEntity, Long>, FreeBoardEntityCustomRepository{

}
