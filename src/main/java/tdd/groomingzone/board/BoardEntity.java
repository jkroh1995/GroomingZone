package tdd.groomingzone.board;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import tdd.groomingzone.board.comment.CommentEntity;
import tdd.groomingzone.global.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "BOARD")
public abstract class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    @Column(name = "WRITE_MEMBER_ID")
    private Long writerId;

    @NotNull
    @Column(name = "TITLE")
    private String title;

    @NotNull
    @Column(name = "CONTENT")
    private String content;

    @Column(name = "VIEW_COUNT")
    private int viewCount = 0;
}