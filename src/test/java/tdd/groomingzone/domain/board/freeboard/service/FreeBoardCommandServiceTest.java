package tdd.groomingzone.domain.board.freeboard.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.util.StubTime;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.dto.FreeBoardDto;
import tdd.groomingzone.domain.board.freeboard.repository.FreeBoardRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FreeBoardCommandServiceTest {

    @Mock
    private FreeBoardRepository freeBoardRepository;

    @InjectMocks
    private FreeBoardCommandService freeBoardCommandService;

    @Test
    void createTest() {
        FreeBoard testEntity = FreeBoard.builder()
                .title("test")
                .content("content")
                .build();
        testEntity.setId(1L);

        given(freeBoardRepository.save(any())).willReturn(testEntity);

        FreeBoard savedEntity = freeBoardCommandService.create(testEntity);

        assertThat(savedEntity.getId()).isEqualTo(1L);
        assertThat(savedEntity.getTitle()).isEqualTo(testEntity.getTitle());
        assertThat(savedEntity.getContent()).isEqualTo(testEntity.getContent());
    }

    @Test
    void updateTest() {
        FreeBoard testEntity = FreeBoard.builder()
                .title("test")
                .content("content")
                .build();
        testEntity.setId(1L);

        FreeBoardDto.Put putDto = new FreeBoardDto.Put();
        putDto.setTitle("modifiedTitle");
        putDto.setContent("modifiedContent");

        try(MockedStatic<StubTime> modifiedAt = mockStatic(StubTime.class)){
            LocalDateTime fakeModifiedTime = LocalDateTime.of(2023, 11, 28, 22, 30 ,10);
            given(StubTime.of(anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt())).willReturn(fakeModifiedTime);

            freeBoardCommandService.update(testEntity, putDto, fakeModifiedTime);

            assertThat(testEntity.getTitle()).isEqualTo(putDto.getTitle());
            assertThat(testEntity.getContent()).isEqualTo(putDto.getContent());
            assertThat(testEntity.getModifiedAt()).isEqualTo(fakeModifiedTime);
        }
    }

    @Test
    void deleteTest(){
        //given
        FreeBoard testEntity = FreeBoard.builder()
                .title("test")
                .content("content")
                .build();
        testEntity.setId(1L);

        //when
        freeBoardCommandService.delete(testEntity);

        //then
        verify(freeBoardRepository).delete(testEntity);
    }
}