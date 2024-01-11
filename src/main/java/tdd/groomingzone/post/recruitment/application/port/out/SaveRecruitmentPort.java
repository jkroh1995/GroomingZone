package tdd.groomingzone.post.recruitment.application.port.out;

public interface SaveRecruitmentPort {
    RecruitmentEntityQueryResult save(SaveRecruitmentQuery saveRecruitmentQuery);
}
