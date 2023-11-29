package tdd.groomingzone.domain.board.freeboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {

}
