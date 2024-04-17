package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.PutFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.SingleFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.LoadFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.SaveFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.util.MemberCreator;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PutFreeBoardCommentServiceTest {

    @Mock
    private LoadFreeBoardCommentPort loadFreeBoardCommentPort;

    @Mock
    private SaveFreeBoardCommentPort saveFreeBoardCommentPort;

    @Mock
    private LoadMemberPort loadMemberPort;

    @InjectMocks
    private PutFreeBoardCommentService putFreeBoardCommentService;

    @Test
    @DisplayName("자유 게시글 댓글을 수정한다.")
    void testPutFreeBoard() {
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
                .modifiedAt(testCreatedAt)
                .build();

        given(loadMemberPort.findMemberByEmail(anyString())).willReturn(writer);

        FreeBoardComment freeBoardComment = FreeBoardComment.builder()
                .writer(writer)
                .freeBoard(freeBoard)
                .id(1L)
                .content("content")
                .createdAt(testCreatedAt)
                .modifiedAt(testModifiedAt)
                .build();
        given(loadFreeBoardCommentPort.loadFreeBoardComment(anyLong())).willReturn(freeBoardComment);
        given(saveFreeBoardCommentPort.save(any())).willReturn(freeBoardComment);

        //when
        PutFreeBoardCommentCommand command = PutFreeBoardCommentCommand.of(writer.getEmail(), 1L, 1L, "content");
        SingleFreeBoardCommentResponse singleFreeBoardCommentResponse = putFreeBoardCommentService.putFreeBoard(command);

        //then
        assertThat(singleFreeBoardCommentResponse.content()).isEqualTo(command.content());
        assertThat(singleFreeBoardCommentResponse.createdAt()).isEqualTo(freeBoardComment.getCreatedAt());
        assertThat(singleFreeBoardCommentResponse.modifiedAt()).isEqualTo(freeBoardComment.getModifiedAt());
    }
}