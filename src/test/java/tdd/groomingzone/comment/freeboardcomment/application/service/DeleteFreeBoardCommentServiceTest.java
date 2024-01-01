package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.board.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.DeleteFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentEntityResult;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.DeleteFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.LoadFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteFreeBoardCommentServiceTest {
    @Mock
    private LoadMemberPort loadMemberPort;

    @Mock
    private LoadFreeBoardCommentPort loadFreeBoardCommentPort;

    @Mock
    private LoadFreeBoardPort loadFreeBoardPort;

    @Mock
    private FreeBoardCommentPublisher freeBoardCommentPublisher;

    @Mock
    private DeleteFreeBoardCommentPort deleteFreeBoardCommentPort;

    @InjectMocks
    private DeleteFreeBoardCommentService deleteFreeBoardCommentService;
    @Test
    void testDelete() {
        //given
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();
        FreeBoardCommentEntityResult freeBoardCommentEntityResult = FreeBoardCommentEntityResult.of(1L,
                1L,
                1L,
                "content",
                testCreatedAt,
                testModifiedAt);
        given(loadFreeBoardCommentPort.loadFreeBoardComment(anyLong())).willReturn(freeBoardCommentEntityResult);

        FreeBoardEntityQueryResult freeBoardEntityQueryResult = FreeBoardEntityQueryResult.of(
                1L,
                "title",
                "content",
                0,
                testCreatedAt,
                testModifiedAt,
                1L);

        given(loadFreeBoardPort.loadFreeBoardById(anyLong())).willReturn(freeBoardEntityQueryResult);

        Member writer = Member.builder()
                .memberId(1L)
                .email("test@email.com")
                .password("11aA!!@@Password")
                .phoneNumber("010-1111-1111")
                .nickName("nickName")
                .role("BARBER")
                .build();

        given(loadMemberPort.findMemberById(anyLong())).willReturn(writer);

        FreeBoard freeBoard = FreeBoard.builder()
                .id(freeBoardEntityQueryResult.getId())
                .title(freeBoardEntityQueryResult.getTitle())
                .content(freeBoardEntityQueryResult.getContent())
                .viewCount(freeBoardEntityQueryResult.getViewCount())
                .createdAt(freeBoardEntityQueryResult.getCreatedAt())
                .modifiedAt(freeBoardEntityQueryResult.getModifiedAt())
                .build();
        FreeBoardComment freeBoardComment = FreeBoardComment.builder()
                .id(1L)
                .freeBoard(freeBoard)
                .content("content")
                .writer(writer)
                .build();
        given(freeBoardCommentPublisher.createFreeBoardComment(any(), any(), any(), any(), any(), any())).willReturn(freeBoardComment);

        //when
        DeleteFreeBoardCommentCommand command = DeleteFreeBoardCommentCommand.of(writer.getMemberId(), freeBoard.getId(), freeBoardComment.getId());
        deleteFreeBoardCommentService.delete(command);

        //then
        verify(deleteFreeBoardCommentPort).delete(freeBoardComment.getId());
    }
}