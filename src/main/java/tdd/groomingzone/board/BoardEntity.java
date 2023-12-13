package tdd.groomingzone.board;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import tdd.groomingzone.board.comment.Comment;
import tdd.groomingzone.global.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private long id;

    @Column(name = "MEMBER_ID")
    private long memberId;

    @NotNull
    @Column(name = "TITLE")
    private String title;

    @NotNull
    @Column(name = "CONTENT")
    private String content;

    @Column(name = "VIEW_COUNT")
    private int viewCount = 0;

    @OneToMany(mappedBy = "boardEntity")
    private List<Comment> comments = new ArrayList<>();
}