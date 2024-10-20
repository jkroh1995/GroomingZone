package tdd.groomingzone.post.recruitment.service;

import tdd.groomingzone.post.recruitment.dto.SingleRecruitmentResponse;
import tdd.groomingzone.post.recruitment.dto.PutRecruitmentCommand;

public interface PutRecruitmentUseCase {
    SingleRecruitmentResponse putRecruitment(PutRecruitmentCommand putRecruitmentCommand);
}
