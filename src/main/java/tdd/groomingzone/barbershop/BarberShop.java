package tdd.groomingzone.barbershop;

import lombok.Getter;
import tdd.groomingzone.member.Member;

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
}
