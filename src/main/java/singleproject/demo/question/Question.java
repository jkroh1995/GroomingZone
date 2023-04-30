package singleproject.demo.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import singleproject.demo.comment.Comment;
import singleproject.demo.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "question")
    private List<Comment> comments;

    public QuestionDto.Response questionToQuestionResponseDto() {
        QuestionDto.Response response = new QuestionDto.Response();
        response.setQuestionId(questionId);
        response.setMemberName(member.getName());
        response.setHeader(header);
        response.setText(text);
        response.setComments(comments);

        return response;
    }
}
