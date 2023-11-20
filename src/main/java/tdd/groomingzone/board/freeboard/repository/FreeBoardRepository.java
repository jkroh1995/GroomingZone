package tdd.groomingzone.board.freeboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tdd.groomingzone.board.freeboard.FreeBoard;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {

}
