package singleproject.demo.posting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import singleproject.demo.member.entity.Member;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Posting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @OneToOne
    private Member member;

    private long viewCount = 0;
}
