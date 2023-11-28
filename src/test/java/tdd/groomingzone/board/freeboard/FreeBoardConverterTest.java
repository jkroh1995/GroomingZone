package tdd.groomingzone.board.freeboard;

import org.junit.jupiter.api.Test;
import tdd.groomingzone.domain.board.freeboard.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.FreeBoardDto;
import tdd.groomingzone.domain.board.freeboard.service.FreeBoardConverter;

import static org.assertj.core.api.Assertions.assertThat;

class FreeBoardConverterTest {

    private final FreeBoardConverter freeBoardConverter = new FreeBoardConverter();

    @Test
    void convertPostDtoToEntityTest(){
        FreeBoardDto.Post postDto = new FreeBoardDto.Post();
        String testTitle = "test";
        String testContent = "content";

        postDto.setTitle(testTitle);
        postDto.setContent(testContent);

        FreeBoard entity = freeBoardConverter.convertPostDtoToEntity(postDto);

        assertThat(postDto.getTitle()).isEqualTo(entity.getTitle());

        assertThat(postDto.getContent()).isEqualTo(entity.getContent());
    }

    @Test
    void convertEntityToResponseDtoTest(){
        FreeBoard entity = FreeBoard.builder()
                .title("title")
                .content("content")
                .build();

        FreeBoardDto.Response responseDto = freeBoardConverter.convertEntityToResponseDto(entity);

        assertThat(entity.getTitle()).isEqualTo(responseDto.getTitle());
        assertThat(entity.getContent()).isEqualTo(responseDto.getContent());
    }
}