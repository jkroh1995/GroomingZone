package tdd.groomingzone.comment.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentEntityCustomRepository {
    Page<CommentEntity> findCommentPageByBoardId(long boardId, Pageable pageable);
}
