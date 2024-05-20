package tdd.groomingzone.global.event.barbershop;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CreateBarberShopEventPublisherImpl implements CreateBarberShopEventPublisher{

    private final ApplicationEventPublisher eventPublisher;

    public CreateBarberShopEventPublisherImpl(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publishEvent(CreateBarberShopEvent event) {
        eventPublisher.publishEvent(event);
    }
}