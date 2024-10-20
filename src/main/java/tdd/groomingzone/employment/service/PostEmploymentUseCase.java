package tdd.groomingzone.employment.service;

import tdd.groomingzone.employment.dto.PostEmploymentCommand;
import tdd.groomingzone.employment.dto.PostEmploymentResponse;

public interface PostEmploymentUseCase {
    PostEmploymentResponse postEmployment(PostEmploymentCommand postEmploymentCommand);
}
