package tdd.groomingzone.reservation;

import lombok.Getter;

import jakarta.persistence.*;

@Entity
@Getter
@Table(name = "RESERVATION", indexes = @Index(name = "IDX__BARBER__ID", columnList = "BARBER_ID"))
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CUSTOMER_ID")
    private Long memberId;

    @Column(name = "BARBER_ID")
    private Long barberId;
}
