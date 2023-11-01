package tdd.groomingzone.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FreeFreeBoardServiceTest {

    @Autowired
    FreeBoardService boardService;

    @Test
    void createBoardTest(){
        FreeBoardDto.Post boardPostDto = new FreeBoardDto.Post();
        FreeBoardDto.Response boardResponseDto = boardService.createFreeBoard(boardPostDto);
        assertThat(boardResponseDto).isNull();
    }
}
