package tdd.groomingzone.board.recruitment.application.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.board.common.WriterInfo;
import tdd.groomingzone.board.recruitment.application.port.in.SingleRecruitmentResponse;
import tdd.groomingzone.board.recruitment.application.port.in.command.PostRecruitmentCommand;
import tdd.groomingzone.board.recruitment.application.port.in.usecase.PostRecruitmentUseCase;
import tdd.groomingzone.board.recruitment.application.port.out.RecruitmentEntityQueryResult;
import tdd.groomingzone.board.recruitment.application.port.out.SaveRecruitmentPort;
import tdd.groomingzone.board.recruitment.application.port.out.SaveRecruitmentQuery;
import tdd.groomingzone.board.recruitment.domain.Recruitment;
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
        Member writer = loadMemberPort.findMemberById(postRecruitmentCommand.getWriterId());
        Recruitment recruitment = Recruitment.builder()
                .id(CommonEnums.NEW_INSTANCE.getValue())
                .writer(writer)
                .title(postRecruitmentCommand.getTitle())
                .content(postRecruitmentCommand.getContent())
                .viewCount(CommonEnums.NEW_INSTANCE.getValue())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .type(postRecruitmentCommand.getType())
                .build();

        SaveRecruitmentQuery saveRecruitmentQuery = SaveRecruitmentQuery.of(recruitment.getWriterId(),
                recruitment.getWriterNickName(),
                recruitment.getId(),
                recruitment.getTitle(),
                recruitment.getContent(),
                recruitment.getViewCount(),
                recruitment.getCreatedAt(),
                recruitment.getModifiedAt(),
                recruitment.getType());

        RecruitmentEntityQueryResult queryResult = saveRecruitmentPort.save(saveRecruitmentQuery);
        return SingleRecruitmentResponse.of(queryResult.getBoardId(),
                queryResult.getTitle(),
                queryResult.getContent(),
                queryResult.getType(),
                queryResult.getViewCount(),
                queryResult.getCreatedAt(),
                queryResult.getModifiedAt(),
                WriterInfo.of(writer));
    }
}
