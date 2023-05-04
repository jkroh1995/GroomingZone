package singleproject.demo.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import singleproject.demo.comment.Comment;

import java.util.List;

public class QuestionDto {

    @Getter
    public static class Post {

        private long memberId;
        private String header;
        private String text;
        private int policyNum;

        public Question questionPostDtoToQuestion() {
            Question question = new Question();
            question.setHeader(header);
            question.setText(text);
            question.setPolicy(policyNum);
            return question;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {

        private long questionId;
        private long memberId;
        private String header;
        private String text;
        private Question.PublicPolicy policy;
        private List<Comment> comments;
    }

    @Getter
    public static class Patch {
        private long memberId;
        private String text;
        private int policyNum;
    }
}
