package singleproject.demo.question;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface QuestionRepository extends Repository<Question, Long> {
    Question save(Question question);

    Optional<Question> findByQuestionId(long questionId);
    void removeByQuestionId(long questionId);
}
