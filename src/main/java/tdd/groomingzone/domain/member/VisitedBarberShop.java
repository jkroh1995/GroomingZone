package tdd.groomingzone.domain.member;

import tdd.groomingzone.domain.member.entity.Member;

import javax.persistence.*;

@Entity
public class VisitedBarberShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member visitor;
}
