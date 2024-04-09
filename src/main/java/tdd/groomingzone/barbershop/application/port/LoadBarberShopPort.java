package tdd.groomingzone.barbershop.application.port;

import tdd.groomingzone.barbershop.domain.BarberShop;

public interface LoadBarberShopPort {
    BarberShop findBarberShopById(Long barberShopId);
}
