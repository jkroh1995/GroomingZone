package tdd.groomingzone.domain.board.freeboard.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FreeBoardRepositoryTest {

    @Autowired
    private FreeBoardRepository freeBoardRepository;

    @Test
    @DisplayName("동적 쿼리를 이용한 검색 조건에 따른 검색이 적절히 이루어지는지 테스트")
    void findFilteredFreeBoardTest(){
        String targetTitle = "targetTitle";
        String targetContent = "targetContent";
        freeBoardRepository.saveAll(Arrays.asList(
                FreeBoard.builder()
                        .title(targetTitle)
                        .content("content")
                        .build(),
                FreeBoard.builder()
                        .title("what")
                        .content("content")
                        .build(),
                FreeBoard.builder()
                        .title("non-target")
                        .content(targetContent)
                        .build()
        ));

        Pageable pageable = PageRequest.ofSize(20).withPage(0);

        Page<FreeBoard> filteredByTitleFreeBoards = freeBoardRepository.findFilteredFreeBoards(targetTitle,"","", pageable);
        Page<FreeBoard> filteredByContentFreeBoards = freeBoardRepository.findFilteredFreeBoards("", targetContent, "", pageable);

        assertThat(filteredByTitleFreeBoards.getContent().size()).isEqualTo(1);
        assertThat(filteredByTitleFreeBoards.getContent().get(0).getContent()).isEqualTo("content");

        assertThat(filteredByContentFreeBoards.getContent().size()).isEqualTo(1);
        assertThat(filteredByContentFreeBoards.getContent().get(0).getTitle()).isEqualTo("non-target");
    }
}