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
import tdd.groomingzone.post.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;
import tdd.groomingzone.util.MemberCreator;

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
        Member writer = MemberCreator.createMember();

        DeleteFreeBoardCommand deleteFreeBoardCommand = DeleteFreeBoardCommand.of(writer.getEmail(), 1L);
        FreeBoard freeBoard = FreeBoard.builder()
                .writer(writer)
                .id(1L)
                .title("title")
                .content("content")
                .viewCount(1)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        given(loadFreeBoardPort.loadFreeBoardById(anyLong())).willReturn(freeBoard);
        given(loadMemberPort.findMemberByEmail(anyString())).willReturn(writer);

        //when
        deleteFreeBoardService.deleteFreeBoard(deleteFreeBoardCommand);

        //then
        verify(deleteFreeBoardPort).delete(anyLong());
    }
}