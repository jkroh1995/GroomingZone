package tdd.groomingzone.barbershop.employment.adapter.out.persistence;

import org.springframework.stereotype.Repository;
import tdd.groomingzone.barbershop.employment.domain.Employment;
import tdd.groomingzone.barbershop.employment.application.port.out.SaveEmploymentPort;

@Repository
public class EmploymentEntityPersistenceAdapter implements SaveEmploymentPort {

    private final EmploymentEntityRepository employmentEntityRepository;
    private final EmploymentMapper employmentMapper;

    public EmploymentEntityPersistenceAdapter(EmploymentEntityRepository employmentEntityRepository, EmploymentMapper employmentMapper) {
        this.employmentEntityRepository = employmentEntityRepository;
        this.employmentMapper = employmentMapper;
    }

    @Override
    public Employment save(Employment employment) {
        EmploymentEntity databaseEntity = employmentMapper.mapToDatabaseEntity(employment);
        return employmentMapper.mapToDomainEntity(databaseEntity);
    }
}
