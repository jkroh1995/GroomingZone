package tdd.groomingzone.board.freeboard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.board.freeboard.service.FreeBoardCommandService;
import tdd.groomingzone.board.freeboard.service.FreeBoardConverter;
import tdd.groomingzone.board.freeboard.service.FreeBoardQueryService;

@ExtendWith(MockitoExtension.class)
class FreeBoardServiceTest {

    @Mock
    private FreeBoardCommandService freeBoardCommandService;

    @Mock
    private FreeBoardQueryService freeBoardQueryService;

    @Mock
    private FreeBoardConverter freeBoardConverter;

    @InjectMocks
    private FreeBoardService freeBoardService;

    @Test
    void createFreeBoardTest(){

    }
}