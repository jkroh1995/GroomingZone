package singleproject.demo.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import singleproject.demo.member.Member;
import singleproject.demo.question.Question;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;
    private String text;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Question question;
}
