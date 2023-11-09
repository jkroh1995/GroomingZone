package tdd.groomingzone.member;

import tdd.groomingzone.review.Review;
import tdd.groomingzone.Wallet;
import tdd.groomingzone.board.freeboard.FreeBoard;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private long id;

    private String name;

    @OneToOne(mappedBy = "member")
    private Wallet wallet;

    @OneToMany(mappedBy = "member")
    private List<FreeBoard> freeBoards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    public void writeFreeBoard(FreeBoard freeBoard){
        this.freeBoards.add(freeBoard);
        if(freeBoard.getMember() != this){
            freeBoard.setMember(this);
        }
    }
}
