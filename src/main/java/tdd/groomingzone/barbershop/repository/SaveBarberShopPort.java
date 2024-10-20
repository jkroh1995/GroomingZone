package tdd.groomingzone.barbershop.repository;

import tdd.groomingzone.barbershop.domain.BarberShop;

public interface SaveBarberShopPort {

    BarberShop save(BarberShop barberShop);
}
