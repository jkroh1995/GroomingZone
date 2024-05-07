package tdd.groomingzone.barbershop.domain;

import lombok.Builder;
import lombok.Getter;
import tdd.groomingzone.member.domain.Member;

@Getter
public class BarberShop {

    private final Long barberShopId;
    private final Member owner;
    private final String name;
    private final Address address;

    @Builder
    private BarberShop(Long barberShopId, Member owner, String name, Address address) {
        this.barberShopId = barberShopId;
        this.owner = owner;
        this.name = name;
        this.address = address;
    }

    public Long getOwnerId() {
        return owner.getMemberId();
    }

    public String getZipCode() {
        return address.zipCode();
    }

    public String getStreetAddress() {
        return address.streetAddress();
    }

    public String getDetailAddress() {
        return address.detailAddress();
    }
}
