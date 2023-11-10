package tdd.groomingzone.board.freeboard;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FreeBoardTest {

    @Test
    void testModify() {
        FreeBoard testEntity = FreeBoard.builder()
                .title("test")
                .content("content")
                .build();
        testEntity.setId(1L);

        FreeBoardDto.Put putDto = new FreeBoardDto.Put();
        putDto.setTitle("modifiedTitle");
        putDto.setContent("modifiedContent");

        testEntity.modify(putDto);

        assertThat(testEntity.getTitle()).isEqualTo(putDto.getTitle());
        assertThat(testEntity.getContent()).isEqualTo(putDto.getContent());
    }
}