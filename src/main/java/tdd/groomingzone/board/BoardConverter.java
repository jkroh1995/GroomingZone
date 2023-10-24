package tdd.groomingzone.board;

import org.springframework.stereotype.Service;

@Service
public class BoardConverter {

    public Board convertPostDtoToEntity(BoardDto.Post boardPostDto) {
        return Board.builder()
                .title(boardPostDto.getTitle())
                .content(boardPostDto.getContent())
                .build();
    }
}
