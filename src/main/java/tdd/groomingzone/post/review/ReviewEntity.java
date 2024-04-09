package tdd.groomingzone.post.review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import tdd.groomingzone.post.common.BoardEntity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "REVIEW")
public class ReviewEntity extends BoardEntity {

    private Long barberShopId;

    @Builder
    private ReviewEntity(Long barberShopId, Long boardId, Long writerId, String writerNickName, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.barberShopId = barberShopId;
        this.setBoardId(boardId);
        this.setWriterId(writerId);
        this.setWriterNickName(writerNickName);
        this.setTitle(title);
        this.setContent(content);
        this.setCreatedAt(createdAt);
        this.setModifiedAt(modifiedAt);
    }
}
