package tdd.groomingzone.barbershop.repository;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "BARBER_SHOP")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private String phoneNumber;

    private long rate = 0;

    @Builder
    private BarberShopEntity(Long ownerId, String name, String zipCode, String streetAddress, String detailAddress, String phoneNumber) {
        this.ownerId = ownerId;
        this.name = name;
        this.zipCode = zipCode;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
        this.phoneNumber = phoneNumber;
    }
}
