package tdd.groomingzone.post.recruitment.application.port.in.usecase;

import tdd.groomingzone.post.recruitment.application.port.in.SingleRecruitmentResponse;
import tdd.groomingzone.post.recruitment.application.port.in.command.PostRecruitmentCommand;

public interface PostRecruitmentUseCase {
    SingleRecruitmentResponse postRecruitment(PostRecruitmentCommand postRecruitmentCommand);
}
