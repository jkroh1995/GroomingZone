package tdd.groomingzone.post.recruitment.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.recruitment.dto.PutRecruitmentCommand;
import tdd.groomingzone.post.recruitment.dto.SingleRecruitmentResponse;
import tdd.groomingzone.post.recruitment.repository.LoadRecruitmentPort;
import tdd.groomingzone.post.recruitment.repository.SaveRecruitmentPort;
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
