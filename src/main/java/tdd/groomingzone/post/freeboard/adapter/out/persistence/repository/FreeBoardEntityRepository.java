package tdd.groomingzone.post.freeboard.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tdd.groomingzone.post.freeboard.adapter.out.persistence.entity.FreeBoardEntity;

public interface FreeBoardEntityRepository extends JpaRepository<FreeBoardEntity, Long>, FreeBoardEntityCustomRepository{

}
