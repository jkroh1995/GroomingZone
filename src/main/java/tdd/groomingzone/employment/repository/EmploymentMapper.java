package tdd.groomingzone.employment.repository;

import org.springframework.stereotype.Component;
import tdd.groomingzone.employment.domain.Employment;

@Component
public class EmploymentMapper {
    public EmploymentEntity mapToDatabaseEntity(Employment employment) {
        return EmploymentEntity.builder()
                .employmentId(employment.getEmploymentId())
                .barberShopId(employment.getWorkPlaceId())
                .memberId(employment.getWorkerId())
                .build();
    }

    public Employment mapToDomainEntity(EmploymentEntity databaseEntity) {
        return null;
    }
}
