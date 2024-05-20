package tdd.groomingzone.global.event.barbershop;

import tdd.groomingzone.barbershop.domain.BarberShop;

public record CreateBarberShopEvent(
        String message,
        String barberShopName,
        String barberShopNumber,
        String barberShopOwnerNickName,
        String barberShopOwnerPhoneNumber
) {
    private final static String MESSAGE = "바버샵이 등록되었습니다.";

    public static CreateBarberShopEvent of(BarberShop barberShop) {
        return new CreateBarberShopEvent(
                MESSAGE,
                barberShop.getName(),
                barberShop.getPhoneNumber(),
                barberShop.getOwnerNickName(),
                barberShop.getOwnerPhoneNumber());
    }
}
