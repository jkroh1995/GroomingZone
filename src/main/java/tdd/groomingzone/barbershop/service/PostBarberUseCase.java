package tdd.groomingzone.barbershop.service;

import tdd.groomingzone.barbershop.dto.SingleBarberShopResponse;
import tdd.groomingzone.barbershop.dto.PostBarberShopCommand;

public interface PostBarberUseCase {
    SingleBarberShopResponse postBarberShop(PostBarberShopCommand command);
}
