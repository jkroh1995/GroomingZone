package tdd.groomingzone.board.freeboard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

        freeBoardCommandService.update(testEntity, putDto);

        assertThat(testEntity.getTitle()).isEqualTo(putDto.getTitle());
        assertThat(testEntity.getContent()).isEqualTo(putDto.getContent());
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