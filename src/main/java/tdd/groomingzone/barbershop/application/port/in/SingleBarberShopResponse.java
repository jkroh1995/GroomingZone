package tdd.groomingzone.barbershop.application.port.in;

import tdd.groomingzone.barbershop.domain.Address;
import tdd.groomingzone.barbershop.domain.BarberShop;
import tdd.groomingzone.member.domain.Member;

public record SingleBarberShopResponse(
        Long barberShopId,
        Member owner,
        String name,
        Address address
) {
    public static SingleBarberShopResponse of(BarberShop savedBarberShop) {
        return new SingleBarberShopResponse(savedBarberShop.getBarberShopId(), savedBarberShop.getOwner(), savedBarberShop.getName(), savedBarberShop.getAddress());
    }
}
