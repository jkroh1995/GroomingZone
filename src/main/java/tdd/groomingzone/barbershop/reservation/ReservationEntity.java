package tdd.groomingzone.barbershop.reservation;

import lombok.Getter;

import jakarta.persistence.*;

@Entity
@Getter
@Table(name = "RESERVATION", indexes = @Index(name = "IDX__BARBER__SHOP__ID", columnList = "BARBER_SHOP_ID"))
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Column(name = "CUSTOMER_ID")
    private Long memberId;

    @Column(name = "BARBER_SHOP_ID")
    private Long barberShopId;
}
