package tdd.groomingzone.post.recruitment.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.post.recruitment.dto.SingleRecruitmentResponse;
import tdd.groomingzone.post.recruitment.dto.PostRecruitmentCommand;
import tdd.groomingzone.post.recruitment.repository.SaveRecruitmentPort;
import tdd.groomingzone.post.recruitment.domain.Recruitment;
import tdd.groomingzone.global.utils.CommonEnums;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

@Service
public class PostRecruitmentService implements PostRecruitmentUseCase {

    private final SaveRecruitmentPort saveRecruitmentPort;
    private final LoadMemberPort loadMemberPort;

    public PostRecruitmentService(SaveRecruitmentPort saveRecruitmentPort, LoadMemberPort loadMemberPort) {
        this.saveRecruitmentPort = saveRecruitmentPort;
        this.loadMemberPort = loadMemberPort;
    }

    @Override
    public SingleRecruitmentResponse postRecruitment(PostRecruitmentCommand postRecruitmentCommand) {
        Member writer = loadMemberPort.findMemberByEmail(postRecruitmentCommand.writerEmail());
        LocalDateTime writeTime = LocalDateTime.now();

        Recruitment recruitment = Recruitment.builder()
                .id(CommonEnums.NEW_INSTANCE.getValue())
                .writer(writer)
                .title(postRecruitmentCommand.title())
                .content(postRecruitmentCommand.content())
                .viewCount(CommonEnums.NEW_INSTANCE.getValue())
                .createdAt(writeTime)
                .modifiedAt(writeTime)
                .type(postRecruitmentCommand.type())
                .build();

        Recruitment savedRecruitment = saveRecruitmentPort.save(recruitment);
        return SingleRecruitmentResponse.of(savedRecruitment);
    }
}
