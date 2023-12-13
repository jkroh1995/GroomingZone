//package tdd.groomingzone.domain.board.freeboard.service;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import tdd.groomingzone.board.freeboard.entity.FreeBoardEntity;
//import tdd.groomingzone.board.freeboard.adapter.out.persistence.FreeBoardEntityEntityRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//class FreeBoardQueryServiceImplTest {
//
//    @Mock
//    private FreeBoardEntityEntityRepository freeBoardEntityRepository;
//
//    @InjectMocks
//    private FreeBoardQueryServiceImpl freeBoardQueryServiceImpl;
//
//    @Test
//    @DisplayName("하나의 자유 게시글을 읽어온다.")
//    void testReadEntityById() {
//        // given
//        long appropriateId = 1L;
//        long inappropriateId = 0L;
//
//        Optional<FreeBoardEntity> testEntity = Optional.of(FreeBoardEntity.builder()
//                .title("title")
//                .content("content")
//                .build());
//
//        given(freeBoardEntityRepository.findById(appropriateId)).willReturn(testEntity);
//        given(freeBoardEntityRepository.findById(inappropriateId)).willReturn(Optional.empty());
//
//        //when
//        FreeBoardEntity foundEntity = freeBoardQueryServiceImpl.readEntityById(appropriateId);
//
//        //then
//        assertThat(foundEntity).isEqualTo(testEntity.get());
//        assertThrows(RuntimeException.class, () -> freeBoardQueryServiceImpl.readEntityById(inappropriateId));
//    }
//
//    @Test
//    @DisplayName("여러 개의 자유 게시글을 읽어온다.")
//    void testReadPagedEntity() {
//        //given
//        int fakePageNumber = 1;
//        Pageable fakePageable = PageRequest.ofSize(20).withPage(fakePageNumber);
//
//        Page<FreeBoardEntity> fakePage = new PageImpl<>(List.of(
//                FreeBoardEntity.builder().title("1").content("1").build(),
//                FreeBoardEntity.builder().title("2").content("2").build(),
//                FreeBoardEntity.builder().title("3").content("3").build(),
//                FreeBoardEntity.builder().title("4").content("4").build(),
//                FreeBoardEntity.builder().title("5").content("5").build()
//        ));
//
//        given((freeBoardEntityRepository.findAll(fakePageable))).willReturn(fakePage);
//
//        //when
//        Page<FreeBoardEntity> foundPage = freeBoardQueryServiceImpl.readPagedEntity(fakePageNumber);
//
//        //then
//        assertThat(foundPage).isEqualTo(fakePage);
//    }
//
//    @Test
//    @DisplayName("검색 조건으로 지정한 게시글들을 읽어온다.")
//    void readFilteredFreeBoardsTest(){
//        int fakePageNumber = 1;
//        String fakeTitle = "title";
//        String fakeContent = "content";
//        String fakeWriter = "writer";
//
//        Page<FreeBoardEntity> fakePage = new PageImpl<>(List.of(
//                FreeBoardEntity.builder().title("1").content("1").build(),
//                FreeBoardEntity.builder().title("2").content("2").build(),
//                FreeBoardEntity.builder().title("3").content("3").build(),
//                FreeBoardEntity.builder().title("4").content("4").build(),
//                FreeBoardEntity.builder().title("5").content("5").build()
//        ));
//
//        given((freeBoardEntityRepository.findFilteredFreeBoards(anyString(), anyString(), anyString(), any()))).willReturn(fakePage);
//
//        //when
//        Page<FreeBoardEntity> foundList = freeBoardQueryServiceImpl.readFilteredEntityPage(fakeTitle, fakeContent, fakeWriter, fakePageNumber);
//
//        //then
//        assertThat(foundList).isEqualTo(fakePage);
//    }
//}