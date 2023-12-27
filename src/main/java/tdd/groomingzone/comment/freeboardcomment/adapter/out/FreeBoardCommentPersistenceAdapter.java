package tdd.groomingzone.comment.freeboardcomment.adapter.out;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import tdd.groomingzone.comment.common.adapter.out.persistence.CommentEntity;
import tdd.groomingzone.comment.common.adapter.out.persistence.CommentEntityRepository;
import tdd.groomingzone.comment.common.application.port.out.CommentPage;
import tdd.groomingzone.comment.common.application.port.out.CommentPageQueryResult;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.LoadFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.SaveFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;

@Repository
public class FreeBoardCommentPersistenceAdapter implements SaveFreeBoardCommentPort, LoadFreeBoardCommentPort {
    private final CommentEntityRepository commentEntityRepository;
    private final FreeBoardCommentMapper freeBoardCommentMapper;

    public FreeBoardCommentPersistenceAdapter(CommentEntityRepository commentEntityRepository, FreeBoardCommentMapper freeBoardCommentMapper) {
        this.commentEntityRepository = commentEntityRepository;
        this.freeBoardCommentMapper = freeBoardCommentMapper;
    }

    @Override
    public void save(FreeBoardComment freeBoardComment) {
        CommentEntity databaseEntity = freeBoardCommentMapper.mapToDatabaseEntity(freeBoardComment);
        commentEntityRepository.save(databaseEntity);
    }

    @Override
    public CommentPageQueryResult loadFreeBoardCommentPage(CommentPage commentPage) {
        Page<CommentEntity> databaseEntityPage = commentEntityRepository.findCommentPageByBoardId(commentPage.getBoardId(), commentPage.getPageable());
        return freeBoardCommentMapper.mapToMultiQueryResult(databaseEntityPage);
    }
}
