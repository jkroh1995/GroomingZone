package tdd.groomingzone.domain.board.freeboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;

public interface FreeBoardCustomRepository {

    Page<FreeBoard> findFilteredFreeBoards(String title, String content, String writer, Pageable pageable);
}
