package tdd.groomingzone.post.recruitment.adapter.out.persistence;

import org.springframework.stereotype.Component;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.recruitment.domain.Recruitment;

@Component
public class RecruitmentMapper {
    private final LoadMemberPort loadMemberPort;

    public RecruitmentMapper(LoadMemberPort loadMemberPort) {
        this.loadMemberPort = loadMemberPort;
    }

    public RecruitmentEntity mapToDatabaseEntity(Recruitment recruitment) {
        return RecruitmentEntity.builder()
                .boardId(recruitment.getBoardId())
                .writerId(recruitment.getWriterId())
                .writerNickName(recruitment.getWriterNickName())
                .title(recruitment.getTitle())
                .content(recruitment.getContent())
                .createdAt(recruitment.getCreatedAt())
                .modifiedAt(recruitment.getModifiedAt())
                .viewCount(recruitment.getViewCount())
                .type(recruitment.getRecruitmentType())
                .build();
    }

    public Recruitment mapToDomainEntity(RecruitmentEntity databaseEntity) {
        Member writer = loadMemberPort.findMemberById(databaseEntity.getWriterId());
        return Recruitment.builder()
                .id(databaseEntity.getBoardId())
                .writer(writer)
                .title(databaseEntity.getTitle())
                .content(databaseEntity.getContent())
                .viewCount(databaseEntity.getViewCount())
                .createdAt(databaseEntity.getCreatedAt())
                .modifiedAt(databaseEntity.getModifiedAt())
                .type(databaseEntity.getType())
                .build();
    }
}
