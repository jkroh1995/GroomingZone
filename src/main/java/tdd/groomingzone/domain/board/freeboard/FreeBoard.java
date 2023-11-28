package tdd.groomingzone.domain.board.freeboard;

import lombok.*;
import tdd.groomingzone.domain.board.Board;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "FREE")
public class FreeBoard extends Board {

    @Builder
    public FreeBoard(String title, String content){
        this.setTitle(title);
        this.setContent(content);
    }

    public void modify(FreeBoardDto.Put putDto) {
        this.setTitle(putDto.getTitle());
        this.setContent(putDto.getContent());
    }
}
