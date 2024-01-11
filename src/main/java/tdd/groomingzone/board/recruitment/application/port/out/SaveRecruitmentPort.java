package tdd.groomingzone.board.recruitment.application.port.out;

public interface SaveRecruitmentPort {
    RecruitmentEntityQueryResult save(SaveRecruitmentQuery saveRecruitmentQuery);
}
