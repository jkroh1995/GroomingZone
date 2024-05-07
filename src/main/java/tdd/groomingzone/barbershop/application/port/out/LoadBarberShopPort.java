package tdd.groomingzone.barbershop.application.port.out;

import tdd.groomingzone.barbershop.domain.BarberShop;

public interface LoadBarberShopPort {
    BarberShop findBarberShopById(Long barberShopId);
}
