package tdd.groomingzone.barbershop;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class BarberShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BARBER_SHOP_ID")
    private long barberShopId;

    private String name;

    private Long ownerId;

    private long rate = 0;
}
