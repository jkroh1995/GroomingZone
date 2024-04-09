package tdd.groomingzone.barbershop.adapter.out.persistence;

import lombok.Getter;

import jakarta.persistence.*;

@Entity
@Getter
@Table(name = "BARBER_SHOP")
public class BarberShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BARBER_SHOP_ID")
    private long barberShopId;

    private String name;

    private Long ownerId;

    private long rate = 0;
}
