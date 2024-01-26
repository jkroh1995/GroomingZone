package tdd.groomingzone.post.recruitment.adapter.out.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.post.recruitment.application.port.out.RecruitmentEntityQueryResult;
import tdd.groomingzone.post.recruitment.application.port.out.SaveRecruitmentPort;
import tdd.groomingzone.post.recruitment.application.port.out.SaveRecruitmentQuery;
import tdd.groomingzone.post.recruitment.application.port.out.LoadRecruitmentPort;

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
    public RecruitmentEntityQueryResult save(SaveRecruitmentQuery query) {
        RecruitmentEntity databaseEntity = recruitmentMapper.mapToDatabaseEntity(query);
        RecruitmentEntity savedDatabaseEntity = recruitmentEntityRepository.save(databaseEntity);
        return recruitmentMapper.mapToEntityQueryResult(savedDatabaseEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public RecruitmentEntityQueryResult loadRecruitmentById(long recruitmentId) {
        RecruitmentEntity databaseEntity = recruitmentEntityRepository.findById(recruitmentId).orElseThrow(() ->
                new BusinessException(ExceptionCode.BOARD_NOT_FOUND));
        return recruitmentMapper.mapToEntityQueryResult(databaseEntity);
    }
}
