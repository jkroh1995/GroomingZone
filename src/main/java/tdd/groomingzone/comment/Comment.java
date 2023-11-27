package tdd.groomingzone.comment;

import lombok.Getter;
import lombok.Setter;
import tdd.groomingzone.board.Board;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;
}
