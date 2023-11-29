package tdd.groomingzone.domain.board.freeboard.entity;

import lombok.*;
import tdd.groomingzone.domain.board.Board;
import tdd.groomingzone.domain.board.freeboard.dto.FreeBoardDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "FREE")
public class FreeBoard extends Board {

    @Builder
    public FreeBoard(String title, String content){
        this.setTitle(title);
        this.setContent(content);
    }

    public void modify(FreeBoardDto.Put putDto, LocalDateTime modifiedAt) {
        this.setTitle(putDto.getTitle());
        this.setContent(putDto.getContent());
        this.setModifiedAt(modifiedAt);
    }
}
