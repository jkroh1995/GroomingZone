package singleproject.demo.question;

import lombok.Getter;
import lombok.Setter;
import singleproject.demo.comment.Comment;

import java.util.List;

public class QuestionDto {

    @Getter
    public static class Post {

        private long memberId;
        private String header;
        private String text;

        public Question questionPostDtoToQuestion() {
            Question question = new Question();
            question.setHeader(header);
            question.setText(text);
            return question;
        }
    }

    @Getter
    @Setter
    public static class Response {

        private long questionId;
        private String memberName;
        private String header;
        private String text;
        private List<Comment> comments;
    }

    @Getter
    public static class Patch {
        private long memberId;
        private String text;
    }
}
