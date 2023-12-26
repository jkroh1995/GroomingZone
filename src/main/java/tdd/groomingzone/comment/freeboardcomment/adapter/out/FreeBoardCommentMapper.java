package tdd.groomingzone.comment.freeboardcomment.adapter.out;

import org.springframework.stereotype.Component;
import tdd.groomingzone.board.freeboard.adapter.out.persistence.entity.FreeBoardEntity;
import tdd.groomingzone.comment.common.adapter.out.persistence.CommentEntity;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;

@Component
public class FreeBoardCommentMapper {
    public CommentEntity mapToDatabaseEntity(FreeBoardComment freeBoardComment) {
        return CommentEntity.builder()
                .boardId(freeBoardComment.getBoardId())
                .writerId(freeBoardComment.getWriterId())
                .writerNickName(freeBoardComment.getWriterNickName())
                .content(freeBoardComment.getContent())
                .build();
    }
}
