package singleproject.demo.like;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import singleproject.demo.member.entity.Member;
import singleproject.demo.posting.entity.Posting;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long recommendId;

    @OneToOne
    private Member member;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Posting posting;
}
