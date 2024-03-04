package tdd.groomingzone.barbershop.employment.application.port.in;

import tdd.groomingzone.barbershop.employment.application.dto.PostEmploymentCommand;
import tdd.groomingzone.barbershop.employment.application.dto.PostEmploymentResponse;

public interface PostEmploymentUseCase {
    PostEmploymentResponse postEmployment(PostEmploymentCommand postEmploymentCommand);
}
