package tdd.groomingzone.barbershop.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.groomingzone.barbershop.application.port.in.SingleBarberShopResponse;
import tdd.groomingzone.barbershop.application.port.in.command.PostBarberShopCommand;
import tdd.groomingzone.barbershop.application.port.in.usecase.PostBarberUseCase;
import tdd.groomingzone.barbershop.application.port.out.SaveBarberShopPort;
import tdd.groomingzone.barbershop.domain.Address;
import tdd.groomingzone.barbershop.domain.BarberShop;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.global.utils.CommonEnums;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

@Service
public class PostBarberShopService implements PostBarberUseCase {
    private final LoadMemberPort loadMemberPort;
    private final SaveBarberShopPort saveBarberShopPort;

    public PostBarberShopService(LoadMemberPort loadMemberPort, SaveBarberShopPort saveBarberShopPort) {
        this.loadMemberPort = loadMemberPort;
        this.saveBarberShopPort = saveBarberShopPort;
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
                .address(new Address(command.zipCode(), command.streetAddress(), command.detailAddress()))
                .build();
        BarberShop savedBarberShop = saveBarberShopPort.save(barberShop);
        return SingleBarberShopResponse.of(savedBarberShop);
    }
}
