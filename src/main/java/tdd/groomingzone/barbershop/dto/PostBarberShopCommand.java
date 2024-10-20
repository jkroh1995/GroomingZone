package tdd.groomingzone.barbershop.dto;

public record PostBarberShopCommand(
        String requestMemberEmail,
        String name,
        String zipCode,
        String streetAddress,
        String detailAddress,
        String shopPhoneNumber
) {
    public static PostBarberShopCommand of(String requestMemberEmail, BarberShopApiDto.Post dto){
        return new PostBarberShopCommand(requestMemberEmail, dto.name(), dto.zipCode(), dto.streetAddress(), dto.detailAddress(), dto.shopPhoneNumber());
    }
}
