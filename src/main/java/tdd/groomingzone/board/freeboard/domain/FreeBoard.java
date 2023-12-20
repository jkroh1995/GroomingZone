package tdd.groomingzone.board.freeboard.domain;

import lombok.Builder;

import tdd.groomingzone.board.common.BoardContent;

import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

public class FreeBoard {
    private final BoardContent boardContent;

    @Builder
    public FreeBoard(long id, Member writer, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt){
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

    public String getTitleValue() {
        return boardContent.getTitle().getTitle();
    }

    public Member getWriter() {
        return boardContent.getWriter();
    }

    public String getContent() {
        return boardContent.getContent().getContent();
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
        return getWriter().getMemberId();
    }

    public String getWriterNickName() {
        return getWriter().getNickName();
    }

    public void checkMemberAuthority(Member member) {
        if(member.isAdmin()){
            return;
        }
        if(getWriterId() != member.getMemberId()){
            throw new BusinessException(ExceptionCode.UNAUTHORIZED);
        }
    }

    public void setWriter(Member writer) {
        boardContent.setWriter(writer);
    }
}
