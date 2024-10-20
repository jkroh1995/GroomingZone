package tdd.groomingzone.post.recruitment.repository;

import tdd.groomingzone.post.recruitment.domain.Recruitment;

public interface LoadRecruitmentPort {
    Recruitment loadRecruitmentById(long recruitmentId);
}
