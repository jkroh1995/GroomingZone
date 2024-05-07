package tdd.groomingzone.barbershop.adapter.out.persistence;

import org.springframework.stereotype.Repository;
import tdd.groomingzone.barbershop.application.port.out.LoadBarberShopPort;
import tdd.groomingzone.barbershop.application.port.out.SaveBarberShopPort;
import tdd.groomingzone.barbershop.domain.BarberShop;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

@Repository
public class BarberShopPersistenceAdapter implements LoadBarberShopPort, SaveBarberShopPort {

    private final BarberShopEntityRepository barberShopEntityRepository;
    private final BarberShopMapper barberShopMapper;

    public BarberShopPersistenceAdapter(BarberShopEntityRepository barberShopEntityRepository, BarberShopMapper barberShopMapper) {
        this.barberShopEntityRepository = barberShopEntityRepository;
        this.barberShopMapper = barberShopMapper;
    }

    @Override
    public BarberShop findBarberShopById(Long barberShopId) {
        BarberShopEntity databaseEntity = barberShopEntityRepository.findById(barberShopId).orElseThrow(() ->
                new BusinessException(ExceptionCode.BARBER_SHOP_NOT_FOUND));
        return barberShopMapper.mapToDomainEntity(databaseEntity);
    }

    @Override
    public BarberShop save(BarberShop domainEntity) {
        BarberShopEntity savedDatabaseEntity = barberShopEntityRepository.save(barberShopMapper.mapToDatabaseEntity(domainEntity));
        return barberShopMapper.mapToDomainEntity(savedDatabaseEntity);
    }
}
