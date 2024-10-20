package tdd.groomingzone.barbershop.dto;

import tdd.groomingzone.barbershop.domain.Address;

public final class BarberShopApiDto {

    public record Post(
            String name,
            String zipCode,
            String streetAddress,
            String detailAddress,
            String shopPhoneNumber
    ) {

    }

    public record Response(
            Long barberShopId,
            String ownerName,
            String barberShopName,
            Address address
    ) {
        public static Response of(SingleBarberShopResponse commandResponse) {
            return new Response(commandResponse.barberShopId(),
                    commandResponse.ownerNickName(),
                    commandResponse.barberShopName(),
                    commandResponse.address());
        }
    }
}
