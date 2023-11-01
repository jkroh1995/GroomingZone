package tdd.groomingzone.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FreeBoardCommandServiceTest {

    @Mock
    private FreeBoardRepository freeBoardRepository;

    @InjectMocks
    private FreeBoardCommandService freeBoardCommandService;

    @Test
    void saveTest() {
        FreeBoard testEntity = FreeBoard.builder()
                .title("test")
                .content("content")
                .build();
        testEntity.setId(1L);

        given(freeBoardRepository.save(any())).willReturn(testEntity);

        FreeBoard savedEntity = freeBoardCommandService.save(testEntity);

        assertThat(savedEntity.getId()).isEqualTo(1L);
        assertThat(savedEntity.getTitle()).isEqualTo(testEntity.getTitle());
        assertThat(savedEntity.getContent()).isEqualTo(testEntity.getContent());
    }
}