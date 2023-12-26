package tdd.groomingzone.comment.common.adapter.out.persistence;

import lombok.*;

import tdd.groomingzone.global.BaseEntity;

import javax.persistence.*;

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

    @Column(name = "WRITE_MEMBER_NICK_NAME")
    private String writerNickName;

    @Column(name = "CONTENT")
    private String content;

    @Builder
    public CommentEntity(Long boardId, Long writerId, String writerNickName, String content){
        this.boardId = boardId;
        this.writerId = writerId;
        this.writerNickName = writerNickName;
        this.content = content;
    }
}
