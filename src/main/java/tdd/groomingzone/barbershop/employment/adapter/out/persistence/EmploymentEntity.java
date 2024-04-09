package tdd.groomingzone.barbershop.employment.adapter.out.persistence;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "EMPLOYMENT", indexes = @Index(name = "IDX__BARBER__SHOP__ID", columnList = "WORK_PLACE_ID"))
public class EmploymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employmentId;

    @Column(name = "WORKER_ID")
    private Long memberId;

    @Column(name = "WORK_PLACE_ID")
    private Long barberShopId;

    @Builder
    private EmploymentEntity(Long employmentId, Long memberId, Long barberShopId){
        this.employmentId = employmentId;
        this.memberId = memberId;
        this.barberShopId = barberShopId;
    }
}
