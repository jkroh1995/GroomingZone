package tdd.groomingzone.post.recruitment.repository;

import tdd.groomingzone.post.recruitment.domain.Recruitment;

public interface SaveRecruitmentPort {
    Recruitment save(Recruitment recruitment);
}
