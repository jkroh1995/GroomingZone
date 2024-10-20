package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.comment.freeboardcomment.service.DeleteFreeBoardCommentService;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;
import tdd.groomingzone.comment.freeboardcomment.dto.DeleteFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.repository.DeleteFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.repository.LoadFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.util.MemberCreator;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteFreeBoardCommentServiceTest {
    @Mock
    private LoadMemberPort loadMemberPort;

    @Mock
    private LoadFreeBoardCommentPort loadFreeBoardCommentPort;

    @Mock
    private DeleteFreeBoardCommentPort deleteFreeBoardCommentPort;

    @InjectMocks
    private DeleteFreeBoardCommentService deleteFreeBoardCommentService;
    @Test
    void testDelete() {
        //given
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();

        Member writer = MemberCreator.createMember();

        FreeBoard freeBoard = FreeBoard.builder()
                .writer(writer)
                .id(1L)
                .title("title")
                .content("content")
                .viewCount(1)
                .createdAt(testCreatedAt)
                .modifiedAt(testModifiedAt)
                .build();

        FreeBoardComment freeBoardComment = FreeBoardComment.builder()
                .freeBoard(freeBoard)
                .writer(writer)
                .content("content")
                .createdAt(testCreatedAt)
                .modifiedAt(testModifiedAt)
                .build();

        given(loadFreeBoardCommentPort.loadFreeBoardComment(anyLong())).willReturn(freeBoardComment);
        given(loadMemberPort.findMemberByEmail(anyString())).willReturn(writer);

        //when
        DeleteFreeBoardCommentCommand command = DeleteFreeBoardCommentCommand.of(writer.getEmail(), freeBoard.getId(), freeBoardComment.getId());
        deleteFreeBoardCommentService.delete(command);

        //then
        verify(deleteFreeBoardCommentPort).delete(freeBoardComment.getId());
    }
}