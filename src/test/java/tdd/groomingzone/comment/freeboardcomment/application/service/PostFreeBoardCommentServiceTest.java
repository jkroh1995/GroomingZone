package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.comment.freeboardcomment.service.PostFreeBoardCommentService;
import tdd.groomingzone.post.freeboard.repository.LoadFreeBoardPort;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;
import tdd.groomingzone.comment.freeboardcomment.dto.PostFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.dto.SingleFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.repository.SaveFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.util.MemberCreator;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostFreeBoardCommentServiceTest {

    @Mock
    private LoadMemberPort loadMemberPort;

    @Mock
    private LoadFreeBoardPort loadFreeBoardPort;

    @Mock
    private SaveFreeBoardCommentPort saveFreeBoardCommentPort;

    @InjectMocks
    private PostFreeBoardCommentService postFreeBoardCommentService;

    @Test
    @DisplayName("자유 게시글에 댓글을 등록한다.")
    void testPostFreeBoardComment() {
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

        given(loadFreeBoardPort.loadFreeBoardById(anyLong())).willReturn(freeBoard);
        given(loadMemberPort.findMemberByEmail(anyString())).willReturn(writer);

        FreeBoardComment freeBoardComment = FreeBoardComment.builder()
                .writer(writer)
                .freeBoard(freeBoard)
                .id(1L)
                .content("content")
                .createdAt(testCreatedAt)
                .modifiedAt(testModifiedAt)
                .build();
        given(saveFreeBoardCommentPort.save(any())).willReturn(freeBoardComment);

        //when
        PostFreeBoardCommentCommand command = PostFreeBoardCommentCommand.of(writer.getEmail(), 1L, "content");
        SingleFreeBoardCommentResponse singleFreeBoardCommentResponse = postFreeBoardCommentService.postFreeBoardComment(command);

        //then
        assertThat(singleFreeBoardCommentResponse.content()).isEqualTo(command.content());
        assertThat(singleFreeBoardCommentResponse.createdAt()).isEqualTo(freeBoardComment.getCreatedAt());
        assertThat(singleFreeBoardCommentResponse.modifiedAt()).isEqualTo(freeBoardComment.getModifiedAt());
    }
}