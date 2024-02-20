package tdd.groomingzone.post.freeboard.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.post.freeboard.application.port.in.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.command.PutFreeBoardCommand;
import tdd.groomingzone.post.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.post.freeboard.application.port.out.SaveFreeBoardPort;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;
import tdd.groomingzone.util.MemberCreator;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PutFreeBoardServiceTest {

    @Mock
    private LoadMemberPort loadMemberPort;

    @Mock
    private LoadFreeBoardPort loadFreeBoardPort;

    @Mock
    private SaveFreeBoardPort saveFreeBoardPort;

    @InjectMocks
    private PutFreeBoardService putFreeBoardService;

    @Test
    @DisplayName("자유 게시글을 수정한다.")
    void testPutFreeBoard(){
        //given
        Member writer = MemberCreator.createMember();

        String testTitle = "title";
        String testContent = "content";
        int testViewCount = 1;
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();

        PutFreeBoardCommand putFreeBoardCommand = PutFreeBoardCommand.of(writer.getEmail(), 1L, testTitle, testContent, testModifiedAt);

        FreeBoard entityQueryResult =FreeBoard.builder()
                .writer(writer)
                .id(1L)
                .title(testTitle)
                .content(testContent)
                .viewCount(testViewCount)
                .createdAt(testCreatedAt)
                .modifiedAt(testModifiedAt)
                .build();

        given(loadFreeBoardPort.loadFreeBoardById(anyLong())).willReturn(entityQueryResult);
        given(loadMemberPort.findMemberByEmail(anyString())).willReturn(writer);
        given(saveFreeBoardPort.save(any())).willReturn(entityQueryResult);

        SingleFreeBoardCommandResponse response = putFreeBoardService.putFreeBoard(putFreeBoardCommand);

        assertThat(response.getBoardId()).isEqualTo(entityQueryResult.getId());
        assertThat(response.getTitle()).isEqualTo(entityQueryResult.getTitle());
        assertThat(response.getContent()).isEqualTo(entityQueryResult.getContent());
        assertThat(response.getViewCount()).isEqualTo(entityQueryResult.getViewCount());
        assertThat(response.getCreatedAt()).isEqualTo(entityQueryResult.getCreatedAt());
        assertThat(response.getModifiedAt()).isEqualTo(entityQueryResult.getModifiedAt());
        assertThat(response.getWriterInfo().getWriterId()).isEqualTo(writer.getMemberId());
        assertThat(response.getWriterInfo().getWriterNickName()).isEqualTo(writer.getNickName());
    }
}