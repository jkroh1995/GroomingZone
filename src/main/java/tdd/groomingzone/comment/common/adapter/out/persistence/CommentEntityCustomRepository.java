package tdd.groomingzone.comment.common.adapter.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentEntityCustomRepository {
    Page<CommentEntity> findCommentPageByBoardId(long boardId, Pageable pageable);
}
