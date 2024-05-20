package tdd.groomingzone.barbershop.domain;

import lombok.Builder;
import lombok.Getter;
import tdd.groomingzone.member.domain.Member;

@Getter
public class BarberShop {

    private final Long barberShopId;
    private final Member owner;
    private final String name;
    private final String phoneNumber;
    private final Address address;

    @Builder
    private BarberShop(Long barberShopId, Member owner, String name, String phoneNumber, Address address) {
        this.barberShopId = barberShopId;
        this.owner = owner;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Long getOwnerId() {
        return owner.getMemberId();
    }

    public String getZipCode() {
        return address.zipCode();
    }

    public String getOwnerNickName() {
        return owner.getNickName();
    }

    public String getStreetAddress() {
        return address.streetAddress();
    }

    public String getDetailAddress() {
        return address.detailAddress();
    }

    public String getOwnerPhoneNumber() {
        return owner.getPhoneNumber();
    }
}
