package tdd.groomingzone.barbershop.repository;

import tdd.groomingzone.barbershop.domain.BarberShop;

public interface LoadBarberShopPort {
    BarberShop findBarberShopById(Long barberShopId);
}
