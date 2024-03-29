package tdd.groomingzone.post.freeboard.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.post.freeboard.application.port.in.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.command.GetFreeBoardCommand;
import tdd.groomingzone.post.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.post.freeboard.application.port.out.SaveFreeBoardPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;
import tdd.groomingzone.util.MemberCreator;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetFreeBoardServiceTest {

    @Mock
    private LoadFreeBoardPort loadFreeBoardPort;

    @Mock
    private SaveFreeBoardPort saveFreeBoardPort;

    @InjectMocks
    private GetFreeBoardService getFreeBoardService;

    @Test
    @DisplayName("하나의 자유 게시글을 조회한다.")
    void testGetFreeBoard() {
        //given
        Member writer = MemberCreator.createMember();

        String testTitle = "title";
        String testContent = "content";
        long testId = 1L;
        int testViewCount = 1;
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();

        FreeBoard freeBoard = FreeBoard.builder()
                .writer(writer)
                .id(testId)
                .title(testTitle)
                .content(testContent)
                .viewCount(testViewCount)
                .createdAt(testCreatedAt)
                .modifiedAt(testModifiedAt)
                .build();
        given(loadFreeBoardPort.loadFreeBoardById(anyLong())).willReturn(freeBoard);

        FreeBoard afterReadFreeBoard = FreeBoard.builder()
                .writer(writer)
                .id(testId)
                .title(testTitle)
                .content(testContent)
                .viewCount(testViewCount + 1)
                .createdAt(testCreatedAt)
                .modifiedAt(testModifiedAt)
                .build();
        given(saveFreeBoardPort.save(any())).willReturn(afterReadFreeBoard);

        //when
        GetFreeBoardCommand getFreeBoardCommand = GetFreeBoardCommand.of(1L);
        SingleFreeBoardCommandResponse response = getFreeBoardService.getFreeBoard(getFreeBoardCommand);

        //then
        assertThat(response.getBoardId()).isEqualTo(afterReadFreeBoard.getId());
        assertThat(response.getTitle()).isEqualTo(afterReadFreeBoard.getTitle());
        assertThat(response.getContent()).isEqualTo(afterReadFreeBoard.getContent());
        assertThat(response.getViewCount()).isEqualTo(afterReadFreeBoard.getViewCount());
        assertThat(response.getCreatedAt()).isEqualTo(afterReadFreeBoard.getCreatedAt());
        assertThat(response.getModifiedAt()).isEqualTo(afterReadFreeBoard.getModifiedAt());
        assertThat(response.getWriterInfo().getWriterId()).isEqualTo(writer.getMemberId());
        assertThat(response.getWriterInfo().getWriterNickName()).isEqualTo(writer.getNickName());
    }
}