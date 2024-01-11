package tdd.groomingzone.post.freeboard.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.post.freeboard.application.port.in.command.DeleteFreeBoardCommand;
import tdd.groomingzone.post.freeboard.application.port.out.DeleteFreeBoardPort;
import tdd.groomingzone.post.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.post.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.post.freeboard.application.port.out.query.DeleteFreeBoardQuery;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteFreeBoardServiceTest {

    @Mock
    private LoadMemberPort loadMemberPort;

    @Mock
    private LoadFreeBoardPort loadFreeBoardPort;

    @Mock
    private DeleteFreeBoardPort deleteFreeBoardPort;

    @InjectMocks
    private DeleteFreeBoardService deleteFreeBoardService;

    @Test
    @DisplayName("게시글 삭제 테스트")
    void deleteTest() {
        //given
        Member writer = Member.builder()
                .memberId(1L)
                .email("test@email.com")
                .password("11aA!!@@Password")
                .phoneNumber("010-1111-1111")
                .nickName("nickName")
                .role("BARBER")
                .build();

        DeleteFreeBoardCommand deleteFreeBoardCommand = DeleteFreeBoardCommand.of(1L, 1L);
        FreeBoardEntityQueryResult entityQueryResult = FreeBoardEntityQueryResult.of(1L, "test", "content", 0, LocalDateTime.now(), LocalDateTime.now(), 1L);

        given(loadFreeBoardPort.loadFreeBoardById(anyLong())).willReturn(entityQueryResult);
        given(loadMemberPort.findMemberById(anyLong())).willReturn(writer);

        try (MockedStatic<DeleteFreeBoardQuery> mockedStatic = mockStatic(DeleteFreeBoardQuery.class)) {
            DeleteFreeBoardQuery deleteQuery = DeleteFreeBoardQuery.of(1L);
            given(DeleteFreeBoardQuery.of(anyLong())).willReturn(deleteQuery);

            //when
            deleteFreeBoardService.deleteFreeBoard(deleteFreeBoardCommand);

            //then
            verify(deleteFreeBoardPort).delete(deleteQuery);
        }
    }
}