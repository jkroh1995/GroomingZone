package tdd.groomingzone.post.review;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tdd.groomingzone.post.common.BoardEntity;
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
