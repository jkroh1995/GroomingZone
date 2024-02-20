package tdd.groomingzone.post.recruitment.application.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.common.BoardInfo;
import tdd.groomingzone.post.recruitment.application.port.in.command.PutRecruitmentCommand;
import tdd.groomingzone.post.recruitment.application.port.in.usecase.PutRecruitmentUseCase;
import tdd.groomingzone.post.recruitment.application.port.in.SingleRecruitmentResponse;
import tdd.groomingzone.post.recruitment.application.port.out.LoadRecruitmentPort;
import tdd.groomingzone.post.recruitment.application.port.out.RecruitmentEntityQueryResult;
import tdd.groomingzone.post.recruitment.application.port.out.SaveRecruitmentPort;
import tdd.groomingzone.post.recruitment.application.port.out.SaveRecruitmentQuery;
import tdd.groomingzone.post.recruitment.domain.Recruitment;

@Service
public class PutRecruitmentService implements PutRecruitmentUseCase {
    private final LoadMemberPort loadMemberPort;
    private final LoadRecruitmentPort loadRecruitmentPort;
    private final SaveRecruitmentPort saveRecruitmentPort;

    public PutRecruitmentService(LoadMemberPort loadMemberPort, LoadRecruitmentPort loadRecruitmentPort, SaveRecruitmentPort saveRecruitmentPort) {
        this.loadMemberPort = loadMemberPort;
        this.loadRecruitmentPort = loadRecruitmentPort;
        this.saveRecruitmentPort = saveRecruitmentPort;
    }

    @Override
    public SingleRecruitmentResponse putRecruitment(PutRecruitmentCommand command) {
        RecruitmentEntityQueryResult selectQueryResult = loadRecruitmentPort.loadRecruitmentById(command.getRecruitmentId());
        Member writer = loadMemberPort.findMemberById(selectQueryResult.getWriterId());
        Recruitment recruitment = Recruitment.builder()
                .id(selectQueryResult.getBoardId())
                .writer(writer)
                .title(selectQueryResult.getTitle())
                .content(selectQueryResult.getContent())
                .type(selectQueryResult.getType())
                .createdAt(selectQueryResult.getCreatedAt())
                .modifiedAt(selectQueryResult.getModifiedAt())
                .viewCount(selectQueryResult.getViewCount())
                .build();

        Member requestMember = loadMemberPort.findMemberByEmail(command.getRequestMemberEmail());
        recruitment.checkMemberAuthority(requestMember);
        recruitment.modify(BoardInfo.builder()
                .title(command.getTitle())
                .content(command.getContent())
                .modifiedAt(command.getModifiedAt())
                .viewCount(recruitment.getViewCount())
                .build());

        SaveRecruitmentQuery saveRecruitmentQuery = SaveRecruitmentQuery.of(recruitment);
        RecruitmentEntityQueryResult queryResult = saveRecruitmentPort.save(saveRecruitmentQuery);
        return SingleRecruitmentResponse.of(queryResult);
    }
}
