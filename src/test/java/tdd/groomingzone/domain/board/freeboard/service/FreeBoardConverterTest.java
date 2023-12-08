package tdd.groomingzone.domain.board.freeboard.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.dto.FreeBoardDto;
import tdd.groomingzone.domain.member.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;

class FreeBoardConverterTest {

    private final FreeBoardConverter freeBoardConverter = new FreeBoardConverter();

    @Test
    void convertPostDtoToEntityTest(){
        FreeBoardDto.Post postDto = new FreeBoardDto.Post();
        String testTitle = "test";
        String testContent = "content";

        postDto.title = testTitle;
        postDto.content = testContent;

        FreeBoard entity = freeBoardConverter.convertPostDtoToEntity(postDto);

        assertThat(postDto.title).isEqualTo(entity.getTitle());
        assertThat(postDto.content).isEqualTo(entity.getContent());
    }

    @Test
    @DisplayName("Entity를 Response DTO로 변환한다.")
    void convertEntityToResponseDtoTest(){
        Member member = Member.builder()
                .name("name")
                .build();
        member.setId(1L);

        FreeBoard entity = FreeBoard.builder()
                .title("title")
                .content("content")
                .build();
        member.writeFreeBoard(entity);

        FreeBoardDto.Response responseDto = freeBoardConverter.convertEntityToResponseDto(entity);

        assertThat(entity.getTitle()).isEqualTo(responseDto.title);
        assertThat(entity.getContent()).isEqualTo(responseDto.content);
        assertThat(entity.getWriter().getId()).isEqualTo(responseDto.writerId);
        assertThat(entity.getWriter().getName()).isEqualTo(responseDto.writerName);
    }
}