package tdd.groomingzone.barbershop.domain;

import lombok.Builder;
import lombok.Getter;
import tdd.groomingzone.member.domain.Member;

@Getter
public class BarberShop {

    private final Long barberShopId;
    private final Member owner;
    private final String name;

    @Builder
    private BarberShop(Long barberShopId, Member owner, String name){
        this.barberShopId = barberShopId;
        this.owner = owner;
        this.name = name;
    }
}
