package tdd.groomingzone.post.recruitment.application.port.in.usecase;

import tdd.groomingzone.post.recruitment.application.port.in.SingleRecruitmentResponse;
import tdd.groomingzone.post.recruitment.application.port.in.command.PutRecruitmentCommand;

public interface PutRecruitmentUseCase {
    SingleRecruitmentResponse putRecruitment(PutRecruitmentCommand putRecruitmentCommand);
}
