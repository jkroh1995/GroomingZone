package tdd.groomingzone.comment.freeboardcomment.adapter.out;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import tdd.groomingzone.comment.common.CommentEntity;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentEntityResult;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentPageResult;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FreeBoardCommentMapper {
    public CommentEntity mapToDatabaseEntity(FreeBoardComment freeBoardComment) {
        return CommentEntity.builder()
                .boardId(freeBoardComment.getBoardId())
                .writerId(freeBoardComment.getWriterId())
                .content(freeBoardComment.getContent())
                .build();
    }

    public FreeBoardCommentPageResult mapToMultiQueryResult(Page<CommentEntity> page) {
        List<FreeBoardCommentEntityResult> queryResults = page.getContent().stream()
                .map(this::mapToSingleQueryResult)
                .collect(Collectors.toList());

        return FreeBoardCommentPageResult.of(queryResults, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    public FreeBoardCommentEntityResult mapToSingleQueryResult(CommentEntity databaseEntity) {
        return FreeBoardCommentEntityResult.of(databaseEntity.getId(),
                databaseEntity.getWriterId(),
                databaseEntity.getBoardId(),
                databaseEntity.getContent(),
                databaseEntity.getCreatedAt(),
                databaseEntity.getModifiedAt());
    }
}
