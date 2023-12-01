package tdd.groomingzone.domain.board.freeboard.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.repository.FreeBoardRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

        given(freeBoardRepository.findById(appropriateId)).willReturn(testEntity);
        given(freeBoardRepository.findById(inappropriateId)).willReturn(Optional.empty());

        //when
        FreeBoard foundEntity = freeBoardQueryService.readEntityById(appropriateId);

        //then
        assertThat(foundEntity).isEqualTo(testEntity.get());
        assertThrows(RuntimeException.class, () -> freeBoardQueryService.readEntityById(inappropriateId));
    }

    @Test
    @DisplayName("여러 개의 자유 게시글을 읽어온다.")
    void testReadPagedEntity() {
        //given
        int fakePageNumber = 1;
        Pageable fakePageable = PageRequest.ofSize(20).withPage(fakePageNumber);

        Page<FreeBoard> fakePage = new PageImpl<>(List.of(
                FreeBoard.builder().title("1").content("1").build(),
                FreeBoard.builder().title("2").content("2").build(),
                FreeBoard.builder().title("3").content("3").build(),
                FreeBoard.builder().title("4").content("4").build(),
                FreeBoard.builder().title("5").content("5").build()
        ));

        given((freeBoardRepository.findAll(fakePageable))).willReturn(fakePage);

        //when
        Page<FreeBoard> foundPage = freeBoardQueryService.readPagedEntity(fakePageNumber);

        //then
        assertThat(foundPage).isEqualTo(fakePage);
    }

    @Test
    @DisplayName("검색 조건으로 지정한 게시글들을 읽어온다.")
    void readFilteredFreeBoardsTest(){
        int fakePageNumber = 1;
        String fakeTitle = "title";
        String fakeContent = "content";
        String fakeWriter = "writer";

        Page<FreeBoard> fakePage = new PageImpl<>(List.of(
                FreeBoard.builder().title("1").content("1").build(),
                FreeBoard.builder().title("2").content("2").build(),
                FreeBoard.builder().title("3").content("3").build(),
                FreeBoard.builder().title("4").content("4").build(),
                FreeBoard.builder().title("5").content("5").build()
        ));

        given((freeBoardRepository.findFilteredFreeBoards(anyString(), anyString(), anyString(), any()))).willReturn(fakePage);

        //when
        Page<FreeBoard> foundList = freeBoardQueryService.readFilteredEntityPage(fakeTitle, fakeContent, fakeWriter, fakePageNumber);

        //then
        assertThat(foundList).isEqualTo(fakePage);
    }
}