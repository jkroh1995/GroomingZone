package tdd.groomingzone.board.freeboard.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardEntityCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.command.PostFreeBoardCommand;
import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.board.freeboard.application.port.out.SaveFreeBoardPort;
import tdd.groomingzone.board.freeboard.application.port.out.query.SaveFreeBoardQuery;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

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
        Member writer = Member.builder()
                .memberId(1L)
                .email("test@email.com")
                .password("11aA!!@@Password")
                .phoneNumber("010-1111-1111")
                .nickName("nickName")
                .role("BARBER")
                .build();

        String testTitle = "title";
        String testContent = "content";
        int testViewCount = 1;
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();

        PostFreeBoardCommand postFreeBoardCommand = PostFreeBoardCommand.of(1L, "test", "content");
        SaveFreeBoardQuery saveFreeBoardQuery = SaveFreeBoardQuery.of(writer.getMemberId(),
                writer.getNickName(),
                1L,
                testTitle,
                testContent,
                testViewCount,
                testCreatedAt,
                testModifiedAt);

        FreeBoardEntityQueryResult entityQueryResult = FreeBoardEntityQueryResult.of(1L,
                testTitle,
                testContent,
                testViewCount,
                testCreatedAt,
                testModifiedAt,
                1L);

        given(saveFreeBoardPort.save(any())).willReturn(entityQueryResult);
        given(loadMemberPort.findMemberById(anyLong())).willReturn(writer);

        FreeBoardEntityCommandResponse response = postFreeBoardService.postFreeBoard(postFreeBoardCommand);

        assertThat(response.getBoardId()).isEqualTo(saveFreeBoardQuery.getBoardId());
        assertThat(response.getTitle()).isEqualTo(saveFreeBoardQuery.getTitle());
        assertThat(response.getContent()).isEqualTo(saveFreeBoardQuery.getContent());
        assertThat(response.getViewCount()).isEqualTo(saveFreeBoardQuery.getViewCount());
        assertThat(response.getCreatedAt()).isEqualTo(saveFreeBoardQuery.getCreatedAt());
        assertThat(response.getModifiedAt()).isEqualTo(saveFreeBoardQuery.getModifiedAt());
        assertThat(response.getWriterInfo().getWriterId()).isEqualTo(writer.getMemberId());
        assertThat(response.getWriterInfo().getWriterNickName()).isEqualTo(writer.getNickName());
    }
}