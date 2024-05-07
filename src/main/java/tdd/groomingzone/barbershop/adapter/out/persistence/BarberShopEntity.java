package tdd.groomingzone.barbershop.adapter.out.persistence;

import lombok.Builder;
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

    private Long ownerId;

    private String name;

    private String zipCode;

    private String streetAddress;

    private String detailAddress;

    private long rate = 0;

    @Builder
    private BarberShopEntity(Long ownerId, String name, String zipCode, String streetAddress, String detailAddress) {
        this.ownerId = ownerId;
        this.name = name;
        this.zipCode = zipCode;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
    }
}
