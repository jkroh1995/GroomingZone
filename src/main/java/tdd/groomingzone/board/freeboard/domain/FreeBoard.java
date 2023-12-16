package tdd.groomingzone.board.freeboard.domain;

import lombok.Builder;

import tdd.groomingzone.board.common.BoardContent;

import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.member.Member;

import java.time.LocalDateTime;

public class FreeBoard {
    private final BoardContent boardContent;

    @Builder
    private FreeBoard(long id, Member writer, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.boardContent = BoardContent.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .viewCount(viewCount)
                .build();
    }

    public void modify(String title, String content, LocalDateTime modifiedAt){
        boardContent.setTitle(title);
        boardContent.setContent(content);
        boardContent.setModifiedAt(modifiedAt);
    }

    public void viewed(){
        boardContent.setViewCount(boardContent.getViewCount()+1);
    }

    public long getId() {
        return boardContent.getId();
    }

    public String getTitle() {
        return boardContent.getTitle();
    }

    public Member getWriter() {
        return boardContent.getWriter();
    }

    public String getContent() {
        return boardContent.getContent();
    }

    public int getViewCount() {
        return boardContent.getViewCount();
    }

    public LocalDateTime getCreatedAt() {
        return boardContent.getCreatedAt();
    }

    public LocalDateTime getModifiedAt() {
        return boardContent.getModifiedAt();
    }

    public long getWriterId() {
        return getWriter().getId();
    }

    public void checkMemberAuthority(long requestMemberId) {
        if(getWriterId() != requestMemberId){
            throw new BusinessException(ExceptionCode.UNAUTHORIZED);
        }
    }
}
