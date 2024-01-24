package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.post.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.post.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.PutFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.SingleFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentEntityResult;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.LoadFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.SaveFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.util.MemberCreator;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PutFreeBoardCommentServiceTest {

    @Mock
    private LoadFreeBoardCommentPort loadFreeBoardCommentPort;

    @Mock
    private SaveFreeBoardCommentPort saveFreeBoardCommentPort;

    @Mock
    private LoadMemberPort loadMemberPort;

    @Mock
    private LoadFreeBoardPort loadFreeBoardPort;

    @Mock
    private FreeBoardCommentPublisher freeBoardCommentPublisher;

    @InjectMocks
    private PutFreeBoardCommentService putFreeBoardCommentService;

    @Test
    @DisplayName("자유 게시글 댓글을 수정한다.")
    void testPutFreeBoard() {
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

        Member writer = MemberCreator.createMember();

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

        FreeBoardCommentEntityResult commentEntityResult = FreeBoardCommentEntityResult.of(1L,
                1L,
                1L,
                "content",
                testCreatedAt,
                testModifiedAt);
        given(saveFreeBoardCommentPort.save(any())).willReturn(commentEntityResult);

        //when
        PutFreeBoardCommentCommand command = PutFreeBoardCommentCommand.of(1L, 1L, 1L, "content");
        SingleFreeBoardCommentResponse singleFreeBoardCommentResponse = putFreeBoardCommentService.putFreeBoard(command);

        //then
        assertThat(singleFreeBoardCommentResponse.getContent()).isEqualTo(command.getContent());
        assertThat(singleFreeBoardCommentResponse.getWriterInfo().getWriterId()).isEqualTo(command.getRequestMemberId());
        assertThat(singleFreeBoardCommentResponse.getCreatedAt()).isEqualTo(commentEntityResult.getCreatedAt());
        assertThat(singleFreeBoardCommentResponse.getModifiedAt()).isEqualTo(commentEntityResult.getModifiedAt());
    }
}