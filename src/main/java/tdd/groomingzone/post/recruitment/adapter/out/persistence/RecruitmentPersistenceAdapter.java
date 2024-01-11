package tdd.groomingzone.post.recruitment.adapter.out.persistence;

import org.springframework.stereotype.Repository;
import tdd.groomingzone.post.recruitment.application.port.out.RecruitmentEntityQueryResult;
import tdd.groomingzone.post.recruitment.application.port.out.SaveRecruitmentPort;
import tdd.groomingzone.post.recruitment.application.port.out.SaveRecruitmentQuery;

@Repository
public class RecruitmentPersistenceAdapter implements SaveRecruitmentPort {

    private final RecruitmentEntityRepository recruitmentEntityRepository;
    private final RecruitmentMapper recruitmentMapper;

    public RecruitmentPersistenceAdapter(RecruitmentEntityRepository recruitmentEntityRepository, RecruitmentMapper recruitmentMapper) {
        this.recruitmentEntityRepository = recruitmentEntityRepository;
        this.recruitmentMapper = recruitmentMapper;
    }

    @Override
    public RecruitmentEntityQueryResult save(SaveRecruitmentQuery query) {
        RecruitmentEntity databaseEntity = recruitmentMapper.mapToDatabaseEntity(query);
        RecruitmentEntity savedDatabaseEntity = recruitmentEntityRepository.save(databaseEntity);
        return recruitmentMapper.mapToEntityQueryResult(savedDatabaseEntity);
    }
}
