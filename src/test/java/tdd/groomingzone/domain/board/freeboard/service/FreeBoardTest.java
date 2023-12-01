package tdd.groomingzone.domain.board.freeboard.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.web.bind.annotation.DeleteMapping;
import tdd.groomingzone.util.StubTime;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.dto.FreeBoardDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;

class FreeBoardTest {

    @Test
    @DisplayName("게시글 수정 테스트")
    void testModify() {
        FreeBoard testEntity = FreeBoard.builder()
                .title("test")
                .content("content")
                .build();
        testEntity.setId(1L);

        FreeBoardDto.Put putDto = new FreeBoardDto.Put();
        putDto.title = "modifiedTitle";
        putDto.content = "modifiedContent";

        try (MockedStatic<StubTime> modifiedAt = mockStatic(StubTime.class)) {
            LocalDateTime fakeModifiedTime = LocalDateTime.of(2023, 11, 28, 22, 30, 10);
            given(StubTime.of(anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt())).willReturn(fakeModifiedTime);

            testEntity.modify(putDto, fakeModifiedTime);
            assertThat(testEntity.getTitle()).isEqualTo(putDto.title);
            assertThat(testEntity.getContent()).isEqualTo(putDto.content);
            assertThat(testEntity.getModifiedAt()).isEqualTo(fakeModifiedTime);
        }
    }

    @Test
    @DisplayName("게시글 조회수 증가 테스트")
    void testViewed() {
        FreeBoard testEntity = FreeBoard.builder()
                .title("test")
                .content("content")
                .build();
        testEntity.setId(1L);

        int formalViewCount = testEntity.getViewCount();

        testEntity.viewed();

        assertThat(testEntity.getViewCount()).isEqualTo(formalViewCount + 1);
    }
}