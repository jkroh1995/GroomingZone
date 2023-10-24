package tdd.groomingzone.board;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardConverterTest {

    private final BoardConverter boardConverter = new BoardConverter();

    @Test
    void convertPostDtoToEntityTest(){
        BoardDto.Post postDto = new BoardDto.Post();
        String testTitle = "test";
        String testContent = "content";

        postDto.setTitle(testTitle);
        postDto.setContent(testContent);

        Board entity = boardConverter.convertPostDtoToEntity(postDto);

        String dtoTitle = postDto.getTitle();
        String entityTitle = entity.getTitle();
        assertThat(dtoTitle).isEqualTo(entityTitle);

        String dtoContent = postDto.getContent();
        String entityContent = entity.getContent();
        assertThat(dtoContent).isEqualTo(entityContent);
    }
}