package tdd.groomingzone.post.recruitment.service;

import tdd.groomingzone.post.recruitment.dto.SingleRecruitmentResponse;
import tdd.groomingzone.post.recruitment.dto.PostRecruitmentCommand;

public interface PostRecruitmentUseCase {
    SingleRecruitmentResponse postRecruitment(PostRecruitmentCommand postRecruitmentCommand);
}
