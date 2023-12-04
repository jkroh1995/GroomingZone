package tdd.groomingzone.domain.member.entity;

import lombok.*;
import tdd.groomingzone.domain.barbershop.BarberShop;
import tdd.groomingzone.domain.board.recruitment.Recruitment;
import tdd.groomingzone.domain.board.review.Review;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;
import tdd.groomingzone.domain.member.VisitedBarberShop;
import tdd.groomingzone.global.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "WORK_PLACE_ID")
    private BarberShop workPlace;

    @OneToMany(mappedBy = "visitor")
    private List<VisitedBarberShop> visitedBarberShops = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<FreeBoard> freeBoards = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<Review> reviews = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Builder
    public Member(String email, String password, String name, String phoneNumber, List<String>roles){
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }
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