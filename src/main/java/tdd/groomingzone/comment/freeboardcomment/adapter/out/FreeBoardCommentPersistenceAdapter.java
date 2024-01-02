package tdd.groomingzone.comment.freeboardcomment.adapter.out;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import tdd.groomingzone.comment.common.CommentEntity;
import tdd.groomingzone.comment.common.CommentEntityRepository;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentEntityResult;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentPageable;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentPageResult;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.DeleteFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.LoadFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.SaveFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

@Repository
public class FreeBoardCommentPersistenceAdapter implements SaveFreeBoardCommentPort, LoadFreeBoardCommentPort, DeleteFreeBoardCommentPort {
    private final CommentEntityRepository commentEntityRepository;
    private final FreeBoardCommentMapper freeBoardCommentMapper;

    public FreeBoardCommentPersistenceAdapter(CommentEntityRepository commentEntityRepository, FreeBoardCommentMapper freeBoardCommentMapper) {
        this.commentEntityRepository = commentEntityRepository;
        this.freeBoardCommentMapper = freeBoardCommentMapper;
    }

    @Override
    public FreeBoardCommentEntityResult save(FreeBoardComment freeBoardComment) {
        CommentEntity databaseEntity = freeBoardCommentMapper.mapToDatabaseEntity(freeBoardComment);
        CommentEntity savedDatabaseEntity = commentEntityRepository.save(databaseEntity);
        return freeBoardCommentMapper.mapToSingleQueryResult(savedDatabaseEntity);
    }

    @Override
    public FreeBoardCommentPageResult loadFreeBoardCommentPage(FreeBoardCommentPageable freeBoardCommentPageable) {
        Page<CommentEntity> databaseEntityPage = commentEntityRepository.findCommentPageByBoardId(freeBoardCommentPageable.getBoardId(), freeBoardCommentPageable.getPageable());
        return freeBoardCommentMapper.mapToMultiQueryResult(databaseEntityPage);
    }

    @Override
    public FreeBoardCommentEntityResult loadFreeBoardComment(long commentId) {
        CommentEntity databaseEntity = commentEntityRepository.findById(commentId).orElseThrow(() ->
                new BusinessException(ExceptionCode.COMMENT_NOT_FOUND));
        return freeBoardCommentMapper.mapToSingleQueryResult(databaseEntity);
    }

    @Override
    public void delete(long commentId) {
        CommentEntity databaseEntity = commentEntityRepository.findById(commentId).orElseThrow(() ->
                new BusinessException(ExceptionCode.COMMENT_NOT_FOUND));
        commentEntityRepository.delete(databaseEntity);
    }
}
