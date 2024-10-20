package tdd.groomingzone.barbershop.repository;

import org.springframework.stereotype.Component;
import tdd.groomingzone.barbershop.domain.Address;
import tdd.groomingzone.barbershop.domain.BarberShop;
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
                .phoneNumber(databaseEntity.getPhoneNumber())
                .address(new Address(databaseEntity.getZipCode(), databaseEntity.getStreetAddress(), databaseEntity.getDetailAddress()))
                .build();
    }

    public BarberShopEntity mapToDatabaseEntity(BarberShop domainEntity) {
        return BarberShopEntity.builder()
                .ownerId(domainEntity.getOwnerId())
                .name(domainEntity.getName())
                .zipCode(domainEntity.getZipCode())
                .streetAddress(domainEntity.getStreetAddress())
                .detailAddress(domainEntity.getDetailAddress())
                .phoneNumber(domainEntity.getPhoneNumber())
                .build();
    }
}
