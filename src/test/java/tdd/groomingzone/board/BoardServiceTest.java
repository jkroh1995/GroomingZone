package tdd.groomingzone.board;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardServiceTest {

    private final BoardService boardService = new BoardService();

    @Test
    void createBoardTest(){
        BoardDto.Post boardPostDto = new BoardDto.Post();
        BoardDto.Response boardResponseDto = boardService.createBoard(boardPostDto);
        assertThat(boardResponseDto).isNull();
    }
}
