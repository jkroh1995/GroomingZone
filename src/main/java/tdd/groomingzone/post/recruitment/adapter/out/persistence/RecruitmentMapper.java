package tdd.groomingzone.post.recruitment.adapter.out.persistence;

import org.springframework.stereotype.Component;
import tdd.groomingzone.post.recruitment.application.port.out.RecruitmentEntityQueryResult;
import tdd.groomingzone.post.recruitment.application.port.out.SaveRecruitmentQuery;

@Component
public class RecruitmentMapper {
    public RecruitmentEntity mapToDatabaseEntity(SaveRecruitmentQuery query) {
        return RecruitmentEntity.builder()
                .boardId(query.getBoardId())
                .writerId(query.getWriterId())
                .writerNickName(query.getWriterNickName())
                .title(query.getTitle())
                .content(query.getContent())
                .createdAt(query.getCreatedAt())
                .modifiedAt(query.getModifiedAt())
                .viewCount(query.getViewCount())
                .type(query.getType())
                .build();
    }

    public RecruitmentEntityQueryResult mapToEntityQueryResult(RecruitmentEntity savedDatabaseEntity) {
        return RecruitmentEntityQueryResult.of(savedDatabaseEntity.getBoardId(),
                savedDatabaseEntity.getWriterId(),
                savedDatabaseEntity.getWriterNickName(),
                savedDatabaseEntity.getTitle(),
                savedDatabaseEntity.getContent(),
                savedDatabaseEntity.getType(),
                savedDatabaseEntity.getViewCount(),
                savedDatabaseEntity.getCreatedAt(),
                savedDatabaseEntity.getModifiedAt());
    }
}
