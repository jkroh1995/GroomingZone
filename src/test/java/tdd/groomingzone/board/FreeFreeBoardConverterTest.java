package tdd.groomingzone.board;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FreeFreeBoardConverterTest {

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