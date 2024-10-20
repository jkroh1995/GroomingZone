package tdd.groomingzone.post.freeboard.repository;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import tdd.groomingzone.post.common.BoardEntity;

import jakarta.persistence.*;
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