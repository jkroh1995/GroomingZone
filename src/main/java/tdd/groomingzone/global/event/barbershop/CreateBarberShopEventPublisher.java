package tdd.groomingzone.global.event.barbershop;

public interface CreateBarberShopEventPublisher {

    void publishEvent(CreateBarberShopEvent event);
}
