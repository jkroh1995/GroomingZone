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
}