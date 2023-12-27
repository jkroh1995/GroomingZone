package tdd.groomingzone.comment.freeboardcomment.adapter.out;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import tdd.groomingzone.comment.common.adapter.out.persistence.CommentEntity;
import tdd.groomingzone.comment.common.application.port.out.CommentEntityQueryResult;
import tdd.groomingzone.comment.common.application.port.out.CommentPageQueryResult;
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

    public CommentPageQueryResult mapToMultiQueryResult(Page<CommentEntity> page) {
        List<CommentEntityQueryResult> queryResults = page.getContent().stream()
                .map(this::mapToQueryResult)
                .collect(Collectors.toList());

        return CommentPageQueryResult.of(queryResults, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    private CommentEntityQueryResult mapToQueryResult(CommentEntity databaseEntity) {
        return CommentEntityQueryResult.of(databaseEntity.getId(),
                databaseEntity.getWriterId(),
                databaseEntity.getBoardId(),
                databaseEntity.getContent(),
                databaseEntity.getCreatedAt(),
                databaseEntity.getModifiedAt());
    }
}
