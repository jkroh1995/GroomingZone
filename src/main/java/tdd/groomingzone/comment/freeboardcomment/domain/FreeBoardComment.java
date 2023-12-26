package tdd.groomingzone.comment.freeboardcomment.domain;

import lombok.Builder;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;
import tdd.groomingzone.comment.common.domain.CommentInfo;
import tdd.groomingzone.comment.common.domain.CommentVO;
import tdd.groomingzone.member.domain.Member;

public class FreeBoardComment {

    private final CommentVO commentVO;
    private final CommentInfo commentInfo;
    private final FreeBoard freeBoard;

    @Builder
    public FreeBoardComment(Member writer, FreeBoard freeBoard, String content) {
        this.commentVO = CommentVO.of(writer);
        this.commentInfo = CommentInfo.builder()
                .content(content)
                .build();
        this.freeBoard = freeBoard;
    }

    public Long getBoardId() {
        return freeBoard.getId();
    }

    public Long getWriterId() {
        return commentVO.getWriter().getMemberId();
    }

    public String getWriterNickName() {
        return commentVO.getWriter().getNickName();
    }

    public String getContent() {
        return commentInfo.getContent();
    }
}
