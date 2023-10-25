package tdd.groomingzone.board;

import org.springframework.stereotype.Service;

@Service
public class FreeBoardConverter {

    public FreeBoard convertPostDtoToEntity(FreeBoardDto.Post boardPostDto) {
        return FreeBoard.builder()
                .title(boardPostDto.getTitle())
                .content(boardPostDto.getContent())
                .build();
    }
}
