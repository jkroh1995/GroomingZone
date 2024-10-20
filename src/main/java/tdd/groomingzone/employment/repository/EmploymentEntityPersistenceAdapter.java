package tdd.groomingzone.employment.repository;

import org.springframework.stereotype.Repository;
import tdd.groomingzone.employment.domain.Employment;

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
