package tdd.groomingzone.board;

import org.springframework.stereotype.Service;

@Service
public class FreeBoardConverter {

    public FreeBoard convertPostDtoToEntity(FreeBoardDto.Post postDto) {
        return FreeBoard.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
    }
}
