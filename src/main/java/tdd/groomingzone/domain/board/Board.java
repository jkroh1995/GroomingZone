package tdd.groomingzone.domain.board;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import tdd.groomingzone.domain.comment.Comment;
import tdd.groomingzone.domain.member.Member;
import tdd.groomingzone.global.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Board extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "BOARD_ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @NotNull
    @Column(name = "TITLE")
    private String title;

    @NotNull
    @Column(name = "CONTENT")
    private String content;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();
}