package tdd.groomingzone.review;

import tdd.groomingzone.barbershop.BarberShop;
import tdd.groomingzone.member.Member;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "BARBER_SHOP_ID")
    private BarberShop barberShop;

    private int rate;

    private String content;
}
