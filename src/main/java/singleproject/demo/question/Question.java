package singleproject.demo.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import singleproject.demo.comment.Comment;
import singleproject.demo.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "board")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    @Column
    @NotBlank(message = "제목을 입력하세요.")
    private String header;

    @Column(length = 1000)
    @NotBlank(message = "내용을 입력하세요.")
    private String text;

    private QuestionStatus questionStatus = QuestionStatus.QUESTION_REGISTERED;

    private PublicPolicy policy;

    public void setPolicy(int num) {
        if (num == 1) {
            policy = PublicPolicy.PUBLIC;
            return;
        }
        policy = PublicPolicy.SECRET;
    }

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "question")
    private List<Comment> comments;

    public enum QuestionStatus {
        QUESTION_REGISTERED("질문 등록 삳태"),
        QUESTION_ANSWERED("답변 완료 삳태"),
        QUESTION_DELETED("질문 삭제 상태"),
        QUESTION_DISABLED("질문 비활성화 상태");

        @Getter
        private final String status;

        QuestionStatus(String status) {
            this.status = status;
        }
    }

    public enum PublicPolicy {
        PUBLIC(1, "공개글 상태"),
        SECRET(2, "비밀글 상태");

        @Getter
        private final int number;

        @Getter
        private final String policy;

        PublicPolicy(int number, String policy) {
            this.number = number;
            this.policy = policy;
        }
    }

    public QuestionDto.Response questionToQuestionResponseDto() {
        QuestionDto.Response response = new QuestionDto.Response();
        response.setQuestionId(questionId);
        response.setMemberName(member.getName());
        response.setHeader(header);
        response.setText(text);
        response.setComments(comments);
        response.setPolicy(policy);

        return response;
    }
}
