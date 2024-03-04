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
    public FreeBoardEntity(Long boardId, Long writerId, String writerNickName, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.setBoardId(boardId);
        this.setWriterId(writerId);
        this.setWriterNickName(writerNickName);
        this.setTitle(title);
        this.setContent(content);
        this.setViewCount(viewCount);
        this.setCreatedAt(createdAt);
        this.setModifiedAt(modifiedAt);
    }
}