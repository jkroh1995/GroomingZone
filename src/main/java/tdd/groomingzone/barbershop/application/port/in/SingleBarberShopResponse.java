package tdd.groomingzone.barbershop.application.port.in;

import tdd.groomingzone.barbershop.domain.Address;
import tdd.groomingzone.barbershop.domain.BarberShop;

public record SingleBarberShopResponse(
        Long barberShopId,
        String barberShopName,
        String barberShopPhoneNumber,
        String ownerNickName,
        String ownerPhoneNumber,
        Address address
) {
    public static SingleBarberShopResponse of(BarberShop savedBarberShop) {
        return new SingleBarberShopResponse(
                savedBarberShop.getBarberShopId(),
                savedBarberShop.getName(),
                savedBarberShop.getPhoneNumber(),
                savedBarberShop.getOwnerNickName(),
                savedBarberShop.getOwnerPhoneNumber(),
                savedBarberShop.getAddress());
    }
}
