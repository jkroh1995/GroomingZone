package tdd.groomingzone.barbershop;

import lombok.Getter;
import tdd.groomingzone.post.review.Review;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class BarberShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BARBER_SHOP_ID")
    private long id;

    private String name;

    @OneToMany(mappedBy = "barberShop")
    private List<Review> reviews = new ArrayList<>();

    @OneToOne
    private MemberEntity owner;
}
