package tdd.groomingzone.barbershop;

import org.springframework.stereotype.Component;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;

@Component
public class BarberShopMapper {
    private final LoadMemberPort loadMemberPort;

    public BarberShopMapper(LoadMemberPort loadMemberPort) {
        this.loadMemberPort = loadMemberPort;
    }

    public BarberShop mapToDomainEntity(BarberShopEntity databaseEntity) {
        return BarberShop.builder()
                .barberShopId(databaseEntity.getBarberShopId())
                .owner(loadMemberPort.findMemberById(databaseEntity.getOwnerId()))
                .name(databaseEntity.getName())
                .build();
    }
}
