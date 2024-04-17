package tdd.groomingzone.post.recruitment.application.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.recruitment.application.port.in.command.PutRecruitmentCommand;
import tdd.groomingzone.post.recruitment.application.port.in.usecase.PutRecruitmentUseCase;
import tdd.groomingzone.post.recruitment.application.port.in.SingleRecruitmentResponse;
import tdd.groomingzone.post.recruitment.application.port.out.LoadRecruitmentPort;
import tdd.groomingzone.post.recruitment.application.port.out.SaveRecruitmentPort;
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
        Recruitment recruitment = loadRecruitmentPort.loadRecruitmentById(command.recruitmentId());
        Member requestMember = loadMemberPort.findMemberByEmail(command.requestMemberEmail());

        recruitment.checkMemberAuthority(requestMember);
        recruitment.modify(command.title(), command.content(), command.modifiedAt());
        Recruitment savedRecruitment = saveRecruitmentPort.save(recruitment);

        return SingleRecruitmentResponse.of(savedRecruitment);
    }
}
