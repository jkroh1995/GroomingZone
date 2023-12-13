package tdd.groomingzone.board.freeboard.domain;

import lombok.Builder;

import tdd.groomingzone.board.common.BoardContent;
import tdd.groomingzone.board.comment.Comment;
import tdd.groomingzone.member.entity.Member;

import java.time.LocalDateTime;
import java.util.List;

public class FreeBoard {
    private final BoardContent boardContent;
    private final List<Comment> comments;

    @Builder
    public FreeBoard(long id, Member writer, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, List<Comment> comments){
        this.boardContent = BoardContent.builder()
                .id(id)
                .member(writer)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .viewCount(viewCount)
                .build();
        this.comments = comments;
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
        return boardContent.getMember();
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
}
