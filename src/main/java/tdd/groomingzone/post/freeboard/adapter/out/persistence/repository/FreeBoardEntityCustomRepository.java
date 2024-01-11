package tdd.groomingzone.post.freeboard.adapter.out.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tdd.groomingzone.post.freeboard.adapter.out.persistence.entity.FreeBoardEntity;

public interface FreeBoardEntityCustomRepository {

    Page<FreeBoardEntity> findFilteredFreeBoards(String title, String content, String writer, Pageable pageable);
}
