package tdd.groomingzone.board.freeboard.adapter.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FreeBoardEntityCustomRepository {

    Page<FreeBoardEntity> findFilteredFreeBoards(String title, String content, String writer, Pageable pageable);
}
