package tdd.groomingzone.post.recruitment.application.port.out;

import tdd.groomingzone.post.recruitment.domain.Recruitment;

public interface LoadRecruitmentPort {
    Recruitment loadRecruitmentById(long recruitmentId);
}
