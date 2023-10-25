package tdd.groomingzone.board;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_board_id")
    private long id;

    private String title;

    private String content;

    @Builder
    public FreeBoard(String title, String content){
        this.title = title;
        this.content = content;
    }
}
