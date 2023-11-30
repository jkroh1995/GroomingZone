package tdd.groomingzone.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tdd.groomingzone.domain.board.recruitment.Recruitment;
import tdd.groomingzone.domain.board.review.Review;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;
import tdd.groomingzone.global.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private long id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "writer")
    private List<FreeBoard> freeBoards = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<Recruitment> recruitments = new ArrayList<>();

    public void writeFreeBoard(FreeBoard freeBoard) {
        this.freeBoards.add(freeBoard);
        if (freeBoard.getWriter() != this) {
            freeBoard.setWriter(this);
        }
    }

    @Getter
    public enum Role {
        ADMIN("관리자"),
        BARBER("바버"),
        CUSTOMER("고객");

        private final String role;

        Role(String role) {
            this.role = role;
        }
    }
}