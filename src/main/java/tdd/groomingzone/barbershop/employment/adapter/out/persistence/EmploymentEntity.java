package tdd.groomingzone.barbershop.employment.adapter.out.persistence;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmploymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employmentId;

    @Column(name = "WORKER_ID")
    @NotNull
    private Long memberId;

    @Column(name = "WORK_PLACE_ID")
    @NotNull
    private Long barberShopId;

    @Builder
    private EmploymentEntity(Long employmentId, Long memberId, Long barberShopId){
        this.employmentId = employmentId;
        this.memberId = memberId;
        this.barberShopId = barberShopId;
    }
}
