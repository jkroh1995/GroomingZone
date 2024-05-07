package tdd.groomingzone.barbershop.domain;

public record Address(
        String zipCode,
        String streetAddress,
        String detailAddress
) {
    @Override
    public String toString() {
        return streetAddress + " " + detailAddress;
    }
}
