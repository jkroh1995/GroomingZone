package tdd.groomingzone.barbershop.application.port.in.usecase;

import tdd.groomingzone.barbershop.application.port.in.SingleBarberShopResponse;
import tdd.groomingzone.barbershop.application.port.in.command.PostBarberShopCommand;

public interface PostBarberUseCase {
    SingleBarberShopResponse postBarberShop(PostBarberShopCommand command);
}
