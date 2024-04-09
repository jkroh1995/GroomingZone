package tdd.groomingzone.post.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tdd.groomingzone.global.BaseEntity;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "BOARD")
public abstract class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long boardId;

    @Column(name = "WRITE_MEMBER_ID")
    private Long writerId;

    @Column(name = "WRITE_MEMBER_NICKNAME")
    private String writerNickName;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "VIEW_COUNT")
    private int viewCount = 0;
}