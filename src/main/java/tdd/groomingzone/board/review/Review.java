package tdd.groomingzone.board.review;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tdd.groomingzone.board.common.BoardEntity;
import tdd.groomingzone.barbershop.BarberShop;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "REVIEW")
public class Review extends BoardEntity {

    @ManyToOne
    @JoinColumn(name = "BARBER_SHOP_ID")
    private BarberShop barberShop;
}
