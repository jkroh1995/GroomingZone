package tdd.groomingzone.domain.board.freeboard.service;

import org.junit.jupiter.api.Test;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.dto.FreeBoardDto;

import static org.assertj.core.api.Assertions.assertThat;

class FreeBoardConverterTest {

    private final FreeBoardConverter freeBoardConverter = new FreeBoardConverter();

    @Test
    void convertPostDtoToEntityTest(){
        FreeBoardDto.Post postDto = new FreeBoardDto.Post();
        String testTitle = "test";
        String testContent = "content";

        postDto.title = testTitle;
        postDto.content = testContent;

        FreeBoard entity = freeBoardConverter.convertPostDtoToEntity(postDto);

        assertThat(postDto.title).isEqualTo(entity.getTitle());
        assertThat(postDto.content).isEqualTo(entity.getContent());
    }

    @Test
    void convertEntityToResponseDtoTest(){
        FreeBoard entity = FreeBoard.builder()
                .title("title")
                .content("content")
                .build();

        FreeBoardDto.Response responseDto = freeBoardConverter.convertEntityToResponseDto(entity);

        assertThat(entity.getTitle()).isEqualTo(responseDto.title);
        assertThat(entity.getContent()).isEqualTo(responseDto.content);
    }
}