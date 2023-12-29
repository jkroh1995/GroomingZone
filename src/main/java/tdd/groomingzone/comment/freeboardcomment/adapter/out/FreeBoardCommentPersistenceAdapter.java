package tdd.groomingzone.comment.freeboardcomment.adapter.out;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import tdd.groomingzone.comment.common.CommentEntity;
import tdd.groomingzone.comment.common.CommentEntityRepository;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentPageable;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentPageResult;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.LoadFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.SaveFreeBoardCommentPort;
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
    public FreeBoardCommentPageResult loadFreeBoardCommentPage(FreeBoardCommentPageable freeBoardCommentPageable) {
        Page<CommentEntity> databaseEntityPage = commentEntityRepository.findCommentPageByBoardId(freeBoardCommentPageable.getBoardId(), freeBoardCommentPageable.getPageable());
        return freeBoardCommentMapper.mapToMultiQueryResult(databaseEntityPage);
    }
}