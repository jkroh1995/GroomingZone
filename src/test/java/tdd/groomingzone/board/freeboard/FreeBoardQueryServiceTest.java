package tdd.groomingzone.board.freeboard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.domain.board.freeboard.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.repository.FreeBoardRepository;
import tdd.groomingzone.domain.board.freeboard.service.FreeBoardQueryService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FreeBoardQueryServiceTest {

    @Mock
    private FreeBoardRepository freeBoardRepository;

    @InjectMocks
    private FreeBoardQueryService freeBoardQueryService;

    @Test
    @DisplayName("하나의 자유 게시글을 읽어온다.")
    void testReadEntityById() {
        // given
        long appropriateId = 1L;
        long inappropriateId = 0L;

        Optional<FreeBoard> testEntity = Optional.of(FreeBoard.builder()
                .title("title")
                .content("content")
                .build());

        //when
        given(freeBoardRepository.findById(appropriateId)).willReturn(testEntity);
        given(freeBoardRepository.findById(inappropriateId)).willReturn(Optional.empty());

        FreeBoard foundEntity = freeBoardQueryService.readEntityById(appropriateId);

        //then
        assertThat(foundEntity).isEqualTo(testEntity.get());
        assertThrows(RuntimeException.class, ()->freeBoardQueryService.readEntityById(inappropriateId));
    }
}