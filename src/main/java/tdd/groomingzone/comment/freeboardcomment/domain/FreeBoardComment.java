package tdd.groomingzone.comment.freeboardcomment.domain;

import lombok.Builder;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;
import tdd.groomingzone.comment.common.CommentDetail;
import tdd.groomingzone.comment.common.CommentVO;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

public class FreeBoardComment {
    private final Member writer;
    private final FreeBoard freeBoard;
    private final CommentVO commentVO;
    private final CommentDetail commentDetail;

    @Builder
    public FreeBoardComment(Long id, Member writer, FreeBoard freeBoard, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.writer = writer;
        this.freeBoard = freeBoard;
        this.commentVO = new CommentVO(id, createdAt);
        this.commentDetail = CommentDetail.builder()
                .content(content)
                .modifiedAt(modifiedAt)
                .build();
    }

    public Long getBoardId() {
        return freeBoard.getId();
    }

    public Long getWriterId() {
        return writer.getMemberId();
    }

    public String getWriterNickName() {
        return writer.getNickName();
    }

    public String getContent() {
        return commentDetail.getContent();
    }

    public LocalDateTime getCreatedAt() {
        return commentVO.createdAt();
    }

    public LocalDateTime getModifiedAt() {
        return commentDetail.getModifiedAt();
    }

    public void checkMemberAuthority(Member requestMember) {
        if (requestMember.isAdmin()) {
            return;
        }
        if (!getWriterId().equals(requestMember.getMemberId())) {
            throw new BusinessException(ExceptionCode.UNAUTHORIZED);
        }
    }

    public void modify(String content, LocalDateTime modifiedAt) {
        commentDetail.modify(content, modifiedAt);
    }

    public long getId() {
        return this.commentVO.commentId();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FreeBoardComment comment) {
            return commentVO.equals(comment.commentVO);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return commentVO.hashCode();
    }
}
