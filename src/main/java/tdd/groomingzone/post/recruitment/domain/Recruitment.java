package tdd.groomingzone.post.recruitment.domain;

import lombok.Builder;
import lombok.Getter;
import tdd.groomingzone.post.common.BoardInfo;
import tdd.groomingzone.post.common.BoardVO;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.common.Post;

import java.time.LocalDateTime;

public class Recruitment implements Post {
    private final BoardVO boardVO;
    private BoardInfo boardInfo;
    private final Type type;

    @Builder
    private Recruitment(long id, Member writer, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, String type) {
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

        this.type = Type.of(type);
    }

    public Long getId() {
        return boardVO.getId();
    }

    public Long getWriterId() {
        return boardVO.getWriter().getMemberId();
    }

    public String getWriterNickName() {
        return boardVO.getWriter().getNickName();
    }

    public LocalDateTime getCreatedAt() {
        return boardVO.getCreatedAt();
    }

    public String getTitle() {
        return boardInfo.getTitle().getTitle();
    }

    public String getContent() {
        return boardInfo.getContent().getContent();
    }

    public int getViewCount() {
        return boardInfo.getViewCount();
    }

    public LocalDateTime getModifiedAt() {
        return boardInfo.getModifiedAt();
    }

    public String getType() {
        return this.type.getType();
    }

    public Member getWriter(){
        return this.boardVO.getWriter();
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

    @Override
    public void modify(BoardInfo boardInfo) {
        this.boardInfo = boardInfo;
    }

    @Getter
    private enum Type {
        OFFER("구인"),
        SEARCH("구직");

        private final String type;

        Type(String type) {
            this.type = type;
        }

        public static Type of(String type) {
            try{
                return Type.valueOf(type);
            }catch (IllegalArgumentException e){
                throw new BusinessException(ExceptionCode.INVALID_RECRUITMENT_TYPE);
            }
        }
    }
}
