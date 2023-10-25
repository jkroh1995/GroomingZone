package tdd.groomingzone.board;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FreeFreeBoardServiceTest {

    private final FreeBoardService boardService = new FreeBoardService();

    @Test
    void createBoardTest(){
        FreeBoardDto.Post boardPostDto = new FreeBoardDto.Post();
        FreeBoardDto.Response boardResponseDto = boardService.createBoard(boardPostDto);
        assertThat(boardResponseDto).isNull();
    }
}
