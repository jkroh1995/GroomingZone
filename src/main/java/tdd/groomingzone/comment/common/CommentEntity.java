package tdd.groomingzone.comment.common;

import lombok.*;

import tdd.groomingzone.global.BaseEntity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "COMMENT")
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BOARD_ID")
    private Long boardId;

    @Column(name = "WRITE_MEMBER_ID")
    private Long writerId;

    @Column(name = "CONTENT")
    private String content;

    @Builder
    public CommentEntity(Long boardId, Long writerId, String content, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.boardId = boardId;
        this.writerId = writerId;
        this.content = content;
        setCreatedAt(createdAt);
        setModifiedAt(modifiedAt);
    }
}
