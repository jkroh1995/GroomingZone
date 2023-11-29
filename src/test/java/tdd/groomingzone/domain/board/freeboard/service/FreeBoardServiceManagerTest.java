package tdd.groomingzone.domain.board.freeboard.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

class FreeBoardServiceManagerTest {

    @MockBean
    private FreeBoardQueryService freeBoardQueryService;

    @MockBean
    private FreeBoardCommandService freeBoardCommandService;

    @MockBean
    private FreeBoardConverter freeBoardConverter;

    @InjectMocks
    private FreeBoardServiceManager freeBoardService;

    @Test
    @DisplayName("페이지 번호가 1보다 작으면 예외처리한다.")
    void testGetFreeBoardPage() {
        int fakePageNumber = 0;
        assertThrows(RuntimeException.class, () -> freeBoardService.getFreeBoardPage(fakePageNumber));
    }
}