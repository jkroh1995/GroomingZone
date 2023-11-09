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

    public FreeBoardDto.Response convertEntityToResponseDto(FreeBoard entity) {
        return FreeBoardDto.Response.builder()
                .title(entity.getTitle())
                .content(entity.getContent())
                .build();
    }
}
