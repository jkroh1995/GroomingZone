package tdd.groomingzone.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FreeBoardCommandServiceTest {

    @Autowired
    private FreeBoardCommandService freeBoardCommandService;

    @Test
    void saveTest(){
        FreeBoard testEntity = FreeBoard.builder()
                .title("test")
                .content("content")
                .build();
        FreeBoard savedEntity = freeBoardCommandService.save(testEntity);

        assertThat(savedEntity.getId()).isEqualTo(1L);
        assertThat(savedEntity.getTitle()).isEqualTo(testEntity.getTitle());
        assertThat(savedEntity.getContent()).isEqualTo(testEntity.getContent());
    }
}