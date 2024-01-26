package tdd.groomingzone.post.freeboard.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.post.freeboard.application.port.in.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.command.GetFreeBoardCommand;
import tdd.groomingzone.post.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.post.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.post.freeboard.application.port.out.SaveFreeBoardPort;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
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

    @Mock
    private LoadMemberPort loadMemberPort;

    @InjectMocks
    private GetFreeBoardService getFreeBoardService;

    @Test
    @DisplayName("하나의 자유 게시글을 조회한다.")
    void testGetFreeBoard(){
        //given
        Member writer = MemberCreator.createMember();
        given(loadMemberPort.findMemberById(anyLong())).willReturn(writer);

        String testTitle = "title";
        String testContent = "content";
        int testViewCount = 1;
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();

        FreeBoardEntityQueryResult beforeReadQueryResult = FreeBoardEntityQueryResult.of(1L,
                testTitle,
                testContent,
                testViewCount,
                testCreatedAt,
                testModifiedAt,
                1L);
        given(loadFreeBoardPort.loadFreeBoardById(anyLong())).willReturn(beforeReadQueryResult);

        FreeBoardEntityQueryResult afterReadQueryResult = FreeBoardEntityQueryResult.of(1L,
                testTitle,
                testContent,
                testViewCount + 1,
                testCreatedAt,
                testModifiedAt,
                1L);
        given(saveFreeBoardPort.save(any())).willReturn(afterReadQueryResult);

        //when
        GetFreeBoardCommand getFreeBoardCommand = GetFreeBoardCommand.of(1L);
        SingleFreeBoardCommandResponse response = getFreeBoardService.getFreeBoard(getFreeBoardCommand);

        //then
        assertThat(response.getBoardId()).isEqualTo(afterReadQueryResult.getId());
        assertThat(response.getTitle()).isEqualTo(afterReadQueryResult.getTitle());
        assertThat(response.getContent()).isEqualTo(afterReadQueryResult.getContent());
        assertThat(response.getViewCount()).isEqualTo(afterReadQueryResult.getViewCount());
        assertThat(response.getCreatedAt()).isEqualTo(afterReadQueryResult.getCreatedAt());
        assertThat(response.getModifiedAt()).isEqualTo(afterReadQueryResult.getModifiedAt());
        assertThat(response.getWriterInfo().getWriterId()).isEqualTo(writer.getMemberId());
        assertThat(response.getWriterInfo().getWriterNickName()).isEqualTo(writer.getNickName());
    }
}