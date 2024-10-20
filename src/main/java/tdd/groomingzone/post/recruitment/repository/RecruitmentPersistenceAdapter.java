package tdd.groomingzone.post.recruitment.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.post.recruitment.domain.Recruitment;

@Repository
public class RecruitmentPersistenceAdapter implements SaveRecruitmentPort, LoadRecruitmentPort {

    private final RecruitmentEntityRepository recruitmentEntityRepository;
    private final RecruitmentMapper recruitmentMapper;

    public RecruitmentPersistenceAdapter(RecruitmentEntityRepository recruitmentEntityRepository, RecruitmentMapper recruitmentMapper) {
        this.recruitmentEntityRepository = recruitmentEntityRepository;
        this.recruitmentMapper = recruitmentMapper;
    }

    @Override
    @Transactional
    public Recruitment save(Recruitment recruitment) {
        RecruitmentEntity databaseEntity = recruitmentMapper.mapToDatabaseEntity(recruitment);
        RecruitmentEntity savedDatabaseEntity = recruitmentEntityRepository.save(databaseEntity);
        return recruitmentMapper.mapToDomainEntity(savedDatabaseEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Recruitment loadRecruitmentById(long recruitmentId) {
        RecruitmentEntity databaseEntity = recruitmentEntityRepository.findById(recruitmentId).orElseThrow(() ->
                new BusinessException(ExceptionCode.BOARD_NOT_FOUND));
        return recruitmentMapper.mapToDomainEntity(databaseEntity);
    }
}
