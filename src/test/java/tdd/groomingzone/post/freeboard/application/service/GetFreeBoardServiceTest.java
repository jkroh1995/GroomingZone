package tdd.groomingzone.post.freeboard.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.post.freeboard.dto.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.dto.GetFreeBoardCommand;
import tdd.groomingzone.post.freeboard.repository.LoadFreeBoardPort;
import tdd.groomingzone.post.freeboard.repository.SaveFreeBoardPort;
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
        assertThat(response.boardId()).isEqualTo(afterReadFreeBoard.getId());
        assertThat(response.title()).isEqualTo(afterReadFreeBoard.getTitle());
        assertThat(response.content()).isEqualTo(afterReadFreeBoard.getContent());
        assertThat(response.viewCount()).isEqualTo(afterReadFreeBoard.getViewCount());
        assertThat(response.createdAt()).isEqualTo(afterReadFreeBoard.getCreatedAt());
        assertThat(response.modifiedAt()).isEqualTo(afterReadFreeBoard.getModifiedAt());
        assertThat(response.writerInfo().getWriterId()).isEqualTo(writer.getMemberId());
        assertThat(response.writerInfo().getWriterNickName()).isEqualTo(writer.getNickName());
    }
}