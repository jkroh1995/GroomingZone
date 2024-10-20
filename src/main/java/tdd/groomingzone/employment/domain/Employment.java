package tdd.groomingzone.employment.domain;

import lombok.Builder;
import lombok.Getter;
import tdd.groomingzone.barbershop.domain.BarberShop;
import tdd.groomingzone.member.domain.Member;

@Getter
public class Employment {
    private final Long employmentId;
    private final BarberShop workPlace;
    private final Member worker;

    @Builder
    private Employment(Long employmentId, BarberShop workPlace, Member worker) {
        this.employmentId = employmentId;
        this.workPlace = workPlace;
        this.worker = worker;
    }

    public Long getWorkPlaceId() {
        return workPlace.getBarberShopId();
    }

    public Long getWorkerId() {
        return worker.getMemberId();
    }

    public String getWorkPlaceName() {
        return workPlace.getName();
    }

    public String getWorkerNickName() {
        return worker.getNickName();
    }
}
