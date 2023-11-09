package tdd.groomingzone.board;

import lombok.*;
import tdd.groomingzone.member.Member;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FREE_BOARD_ID")
    private long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public FreeBoard(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void modify(FreeBoardDto.Put putDto) {
        this.title = putDto.getTitle();
        this.content = putDto.getContent();
    }
}
