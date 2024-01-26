package tdd.groomingzone.post.recruitment.application.port.out;

public interface LoadRecruitmentPort {
    RecruitmentEntityQueryResult loadRecruitmentById(long recruitmentId);
}
