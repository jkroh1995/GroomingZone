package tdd.groomingzone.board.recruitment.application.port.in.usecase;

import tdd.groomingzone.board.recruitment.application.port.in.SingleRecruitmentResponse;
import tdd.groomingzone.board.recruitment.application.port.in.command.PostRecruitmentCommand;

public interface PostRecruitmentUseCase {
    SingleRecruitmentResponse postRecruitment(PostRecruitmentCommand postRecruitmentCommand);
}
