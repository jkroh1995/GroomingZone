package tdd.groomingzone.board.freeboard;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import tdd.groomingzone.util.StubTime;
import tdd.groomingzone.domain.board.freeboard.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.dto.FreeBoardDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;

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

        try(MockedStatic<StubTime> modifiedAt = mockStatic(StubTime.class)){
            LocalDateTime fakeModifiedTime = LocalDateTime.of(2023, 11, 28, 22, 30, 10);
            given(StubTime.of(anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt())).willReturn(fakeModifiedTime);

            testEntity.modify(putDto, fakeModifiedTime);
            assertThat(testEntity.getTitle()).isEqualTo(putDto.getTitle());
            assertThat(testEntity.getContent()).isEqualTo(putDto.getContent());
            assertThat(testEntity.getModifiedAt()).isEqualTo(fakeModifiedTime);
        }
    }
}