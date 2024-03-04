package tdd.groomingzone.barbershop;

import org.springframework.stereotype.Repository;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

@Repository
public class BarberShopPersistenceAdapter implements LoadBarberShopPort {

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
}
