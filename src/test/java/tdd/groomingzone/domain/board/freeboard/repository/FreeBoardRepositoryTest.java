package tdd.groomingzone.domain.board.freeboard.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;
import tdd.groomingzone.domain.member.entity.Member;
import tdd.groomingzone.util.RepositoryTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
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

    @Test
    @DisplayName("게시글 등록 테스트")
    void testPostFreeBoard(){
        Member member = Member.builder()
                .name("name")
                .build();
        member.setId(1L);

        FreeBoard entity = FreeBoard.builder()
                .title("title")
                .content("content")
                .build();
        member.writeFreeBoard(entity);

        FreeBoard savedEntity = freeBoardRepository.save(entity);

        assertThat(savedEntity.getTitle()).isEqualTo(entity.getTitle());
        assertThat(savedEntity.getContent()).isEqualTo(entity.getContent());
        assertThat(savedEntity.getWriter()).isEqualTo(member);
    }
}