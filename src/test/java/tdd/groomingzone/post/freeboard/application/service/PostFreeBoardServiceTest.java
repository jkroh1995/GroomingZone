package tdd.groomingzone.post.freeboard.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.post.freeboard.dto.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.dto.PostFreeBoardCommand;
import tdd.groomingzone.post.freeboard.repository.SaveFreeBoardPort;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;
import tdd.groomingzone.util.MemberCreator;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostFreeBoardServiceTest {

    @Mock
    private LoadMemberPort loadMemberPort;

    @Mock
    private SaveFreeBoardPort saveFreeBoardPort;

    @InjectMocks
    private PostFreeBoardService postFreeBoardService;

    @Test
    @DisplayName("자유 게시글을 저장한다.")
    void testPostFreeBoard(){
        //given
        Member writer = MemberCreator.createMember();

        String testTitle = "title";
        String testContent = "content";
        int testViewCount = 1;
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();

        PostFreeBoardCommand postFreeBoardCommand = PostFreeBoardCommand.of(writer.getEmail(), "test", "content");

        FreeBoard freeBoard = FreeBoard.builder()
                .writer(writer)
                .id(1L)
                .title(testTitle)
                .content(testContent)
                .viewCount(testViewCount)
                .createdAt(testCreatedAt)
                .modifiedAt(testModifiedAt)
                .build();

        given(saveFreeBoardPort.save(any())).willReturn(freeBoard);
        given(loadMemberPort.findMemberByEmail(anyString())).willReturn(writer);

        SingleFreeBoardCommandResponse response = postFreeBoardService.postFreeBoard(postFreeBoardCommand);

        assertThat(response.boardId()).isEqualTo(freeBoard.getId());
        assertThat(response.title()).isEqualTo(freeBoard.getTitle());
        assertThat(response.content()).isEqualTo(freeBoard.getContent());
        assertThat(response.viewCount()).isEqualTo(freeBoard.getViewCount());
        assertThat(response.createdAt()).isEqualTo(freeBoard.getCreatedAt());
        assertThat(response.modifiedAt()).isEqualTo(freeBoard.getModifiedAt());
        assertThat(response.writerInfo().getWriterId()).isEqualTo(writer.getMemberId());
        assertThat(response.writerInfo().getWriterNickName()).isEqualTo(writer.getNickName());
    }
}