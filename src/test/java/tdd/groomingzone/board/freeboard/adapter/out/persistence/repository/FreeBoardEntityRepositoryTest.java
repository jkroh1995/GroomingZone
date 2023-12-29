package tdd.groomingzone.board.freeboard.adapter.out.persistence.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import tdd.groomingzone.board.freeboard.adapter.out.persistence.entity.FreeBoardEntity;
import tdd.groomingzone.util.RepositoryTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class FreeBoardEntityRepositoryTest {

    @Autowired
    private FreeBoardEntityRepository freeBoardEntityRepository;

    @Test
    @DisplayName("동적 쿼리를 이용한 검색 조건에 따른 검색이 적절히 이루어지는지 테스트")
    void findFilteredFreeBoardTest(){
        String targetTitle = "targetTitle";
        String targetContent = "targetContent";
        freeBoardEntityRepository.saveAll(Arrays.asList(
                FreeBoardEntity.builder()
                        .title(targetTitle)
                        .content("content")
                        .build(),
                FreeBoardEntity.builder()
                        .title("what")
                        .content("content")
                        .build(),
                FreeBoardEntity.builder()
                        .title("non-target")
                        .content(targetContent)
                        .build()
        ));

        Pageable pageable = PageRequest.ofSize(20).withPage(0);

        Page<FreeBoardEntity> filteredByTitleFreeBoards = freeBoardEntityRepository.findFilteredFreeBoards(targetTitle,"","", pageable);
        Page<FreeBoardEntity> filteredByContentFreeBoards = freeBoardEntityRepository.findFilteredFreeBoards("", targetContent, "", pageable);

        assertThat(filteredByTitleFreeBoards.getContent().size()).isEqualTo(1);
        assertThat(filteredByTitleFreeBoards.getContent().get(0).getContent()).isEqualTo("content");

        assertThat(filteredByContentFreeBoards.getContent().size()).isEqualTo(1);
        assertThat(filteredByContentFreeBoards.getContent().get(0).getTitle()).isEqualTo("non-target");
    }
}