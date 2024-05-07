package tdd.groomingzone.barbershop.employment.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.groomingzone.barbershop.domain.BarberShop;
import tdd.groomingzone.barbershop.application.port.out.LoadBarberShopPort;
import tdd.groomingzone.barbershop.employment.application.port.in.PostEmploymentUseCase;
import tdd.groomingzone.barbershop.employment.application.port.out.SaveEmploymentPort;
import tdd.groomingzone.barbershop.employment.application.dto.PostEmploymentCommand;
import tdd.groomingzone.barbershop.employment.application.dto.PostEmploymentResponse;
import tdd.groomingzone.barbershop.employment.domain.Employment;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

@Service
public class PostEmploymentService implements PostEmploymentUseCase {

    private final LoadMemberPort loadMemberPort;
    private final LoadBarberShopPort loadBarberShopPort;
    private final SaveEmploymentPort saveEmploymentPort;

    public PostEmploymentService(LoadMemberPort loadMemberPort, LoadBarberShopPort loadBarberShopPort, SaveEmploymentPort saveEmploymentPort) {
        this.loadMemberPort = loadMemberPort;
        this.loadBarberShopPort = loadBarberShopPort;
        this.saveEmploymentPort = saveEmploymentPort;
    }

    @Override
    @Transactional
    public PostEmploymentResponse postEmployment(PostEmploymentCommand command) {
        Member requestMember = loadMemberPort.findMemberByEmail(command.getRequestMemberEmail());
        BarberShop barberShop = loadBarberShopPort.findBarberShopById(command.getBarberShopId());
        if(requestMemberIsNotOwner(barberShop, requestMember)){
            throw new BusinessException(ExceptionCode.UNAUTHORIZED);
        }
        Member worker = loadMemberPort.findMemberById(command.getWorkerId());
        Employment employment = Employment.builder()
                .employmentId(0L)
                .workPlace(barberShop)
                .worker(worker)
                .build();
        Employment savedEmployment = saveEmploymentPort.save(employment);
        return PostEmploymentResponse.of(savedEmployment.getWorkPlaceId(), savedEmployment.getWorkPlaceName(), savedEmployment.getWorkerId(), savedEmployment.getWorkerNickName());
    }

    private static boolean requestMemberIsNotOwner(BarberShop barberShop, Member requestMember) {
        return !barberShop.getOwner().equals(requestMember);
    }
}
