package tdd.groomingzone.board.freeboard.adapter.out.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tdd.groomingzone.board.freeboard.adapter.out.persistence.entity.FreeBoardEntity;

public interface FreeBoardEntityCustomRepository {

    Page<FreeBoardEntity> findFilteredFreeBoards(String title, String content, String writer, Pageable pageable);
}
