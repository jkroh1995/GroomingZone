package tdd.groomingzone.board.freeboard.adapter.out.persistence.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import tdd.groomingzone.board.BoardEntity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "FREE")
public class FreeBoardEntity extends BoardEntity {

    @Builder
    public FreeBoardEntity(Long boardId, Long writerId, String writerNickName, String title, String content){
        this.setId(boardId);
        this.setWriterId(writerId);
        this.setWriterNickName(writerNickName);
        this.setTitle(title);
        this.setContent(content);
    }
}
