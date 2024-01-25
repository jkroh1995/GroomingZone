package tdd.groomingzone.post.freeboard.domain;

import lombok.Builder;

import tdd.groomingzone.post.common.BoardInfo;
import tdd.groomingzone.post.common.BoardVO;

import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.common.Post;

import java.time.LocalDateTime;

public class FreeBoard implements Post {
    private final BoardVO boardVO;
    private BoardInfo boardInfo;

    @Builder
    public FreeBoard(long id, Member writer, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.boardVO = BoardVO.builder()
                .id(id)
                .writer(writer)
                .createdAt(createdAt)
                .build();

        this.boardInfo = BoardInfo.builder()
                .title(title)
                .content(content)
                .modifiedAt(modifiedAt)
                .viewCount(viewCount)
                .build();
    }

    @Override
    public void modify(BoardInfo boardInfo) {
        this.boardInfo = boardInfo;
    }

    public void viewed() {
        boardInfo.setViewCount(boardInfo.getViewCount() + 1);
    }

    @Override
    public void checkMemberAuthority(Member member) {
        if (member.isAdmin()) {
            return;
        }
        if (getWriterId() != member.getMemberId()) {
            throw new BusinessException(ExceptionCode.UNAUTHORIZED);
        }
    }

    public long getId() {
        return boardVO.getId();
    }

    public String getTitle() {
        return boardInfo.getTitle().getTitle();
    }

    public Member getWriter() {
        return boardVO.getWriter();
    }

    public String getContent() {
        return boardInfo.getContent().getContent();
    }

    public int getViewCount() {
        return boardInfo.getViewCount();
    }

    public LocalDateTime getCreatedAt() {
        return boardVO.getCreatedAt();
    }

    public LocalDateTime getModifiedAt() {
        return boardInfo.getModifiedAt();
    }

    public long getWriterId() {
        return getWriter().getMemberId();
    }

    public String getWriterNickName() {
        return getWriter().getNickName();
    }
}
