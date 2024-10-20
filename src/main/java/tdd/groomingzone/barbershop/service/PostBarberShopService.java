package tdd.groomingzone.barbershop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.groomingzone.barbershop.dto.SingleBarberShopResponse;
import tdd.groomingzone.barbershop.dto.PostBarberShopCommand;
import tdd.groomingzone.barbershop.repository.SaveBarberShopPort;
import tdd.groomingzone.barbershop.domain.Address;
import tdd.groomingzone.barbershop.domain.BarberShop;
import tdd.groomingzone.global.event.barbershop.CreateBarberShopEvent;
import tdd.groomingzone.global.event.barbershop.CreateBarberShopEventPublisher;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.global.utils.CommonEnums;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

@Service
public class PostBarberShopService implements PostBarberUseCase {
    private final LoadMemberPort loadMemberPort;
    private final SaveBarberShopPort saveBarberShopPort;
    private final CreateBarberShopEventPublisher eventPublisher;

    public PostBarberShopService(LoadMemberPort loadMemberPort, SaveBarberShopPort saveBarberShopPort, CreateBarberShopEventPublisher eventPublisher) {
        this.loadMemberPort = loadMemberPort;
        this.saveBarberShopPort = saveBarberShopPort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public SingleBarberShopResponse postBarberShop(PostBarberShopCommand command) {
        Member requestMember = loadMemberPort.findMemberByEmail(command.requestMemberEmail());
        if(requestMember.isCustomer()){
            throw new BusinessException(ExceptionCode.UNAUTHORIZED);
        }
        BarberShop barberShop = BarberShop.builder()
                .barberShopId((long) CommonEnums.NEW_INSTANCE.getValue())
                .owner(requestMember)
                .name(command.name())
                .phoneNumber(command.shopPhoneNumber())
                .address(new Address(command.zipCode(), command.streetAddress(), command.detailAddress()))
                .build();
        BarberShop savedBarberShop = saveBarberShopPort.save(barberShop);
        CreateBarberShopEvent event = CreateBarberShopEvent.of(savedBarberShop);
        eventPublisher.publishEvent(event);
        return SingleBarberShopResponse.of(savedBarberShop);
    }
}
