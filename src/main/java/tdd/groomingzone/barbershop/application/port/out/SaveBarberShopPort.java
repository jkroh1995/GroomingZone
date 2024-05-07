package tdd.groomingzone.barbershop.application.port.out;

import tdd.groomingzone.barbershop.domain.BarberShop;

public interface SaveBarberShopPort {

    BarberShop save(BarberShop barberShop);
}
