package tdd.groomingzone.domain.comment;

import lombok.Getter;
import lombok.Setter;
import tdd.groomingzone.domain.board.Board;
import tdd.groomingzone.global.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;
}
