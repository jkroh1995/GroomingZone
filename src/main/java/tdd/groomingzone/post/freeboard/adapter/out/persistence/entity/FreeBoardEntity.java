package tdd.groomingzone.post.freeboard.adapter.out.persistence.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import tdd.groomingzone.post.common.BoardEntity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "FREE")
public class FreeBoardEntity extends BoardEntity {

    @Builder
    public FreeBoardEntity(Long boardId, Long writerId, String writerNickName, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.setId(boardId);
        this.setWriterId(writerId);
        this.setWriterNickName(writerNickName);
        this.setTitle(title);
        this.setContent(content);
        this.setCreatedAt(createdAt);
        this.setModifiedAt(modifiedAt);
    }
}
