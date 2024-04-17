package tdd.groomingzone.comment.freeboardcomment.domain;

import lombok.Builder;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;
import tdd.groomingzone.comment.common.CommentInfo;
import tdd.groomingzone.comment.common.CommentVO;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

public class FreeBoardComment {

    private final CommentVO commentVO;
    private final CommentInfo commentInfo;
    private final FreeBoard freeBoard;

    @Builder
    public FreeBoardComment(long id, Member writer, FreeBoard freeBoard, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.commentVO = CommentVO.of(id, writer, createdAt);
        this.commentInfo = CommentInfo.builder()
                .content(content)
                .modifiedAt(modifiedAt)
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

    public LocalDateTime getCreatedAt() {
        return commentVO.getCreatedAt();
    }

    public LocalDateTime getModifiedAt() {
        return commentInfo.getModifiedAt();
    }

    public void checkMemberAuthority(Member requestMember) {
        if(requestMember.isAdmin()){
            return;
        }
        if(!getWriterId().equals(requestMember.getMemberId())){
            throw new BusinessException(ExceptionCode.UNAUTHORIZED);
        }
    }

    public void modify(String content, LocalDateTime modifiedAt) {
        commentInfo.modify(content, modifiedAt);
    }

    public long getId() {
        return this.commentVO.getId();
    }
}
