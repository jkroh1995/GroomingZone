package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.board.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.GetFreeBoardCommentPageCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.MultiFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.SingleFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentEntityResult;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentPageResult;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.LoadFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetFreeBoardCommentServiceTest {

    @Mock
    private LoadFreeBoardCommentPort loadFreeBoardCommentPort;

    @Mock
    private LoadFreeBoardPort loadFreeBoardPort;

    @Mock
    private LoadMemberPort loadMemberPort;

    @Mock
    private FreeBoardCommentPublisher freeBoardCommentPublisher;

    @InjectMocks
    private GetFreeBoardCommentService getFreeBoardCommentService;

    @Test
    void testGetFreeBoardComment() {
        //given
        long testBoardId = 1L;
        int testPageNumber = 1;
        long testMemberId = 1L;
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();

        GetFreeBoardCommentPageCommand command = GetFreeBoardCommentPageCommand.of(testBoardId, testPageNumber);

        List<FreeBoardCommentEntityResult> entityResultList = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            entityResultList.add(FreeBoardCommentEntityResult.of(
                    i,
                    testMemberId,
                    testBoardId,
                    "content",
                    testCreatedAt,
                    testModifiedAt));
        }

        int testPageIndex = 0;
        int testPageSize = 20;
        long testTotalElements = 20;
        int testTotalPages = 1;
        FreeBoardCommentPageResult commentPageResult = FreeBoardCommentPageResult.of(entityResultList,
                testPageIndex, testPageSize, testTotalElements, testTotalPages);
        given(loadFreeBoardCommentPort.loadFreeBoardCommentPage(any())).willReturn(commentPageResult);

        FreeBoardEntityQueryResult freeBoardEntityQueryResult = FreeBoardEntityQueryResult.of(
                testBoardId,
                "title",
                "content",
                0,
                testCreatedAt,
                testModifiedAt,
                testMemberId);

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
                .createdAt(testCreatedAt)
                .modifiedAt(testModifiedAt)
                .build();
        given(freeBoardCommentPublisher.createFreeBoardComment(any(), any(), any(), any(), any(), any())).willReturn(freeBoardComment);

        //when
        MultiFreeBoardCommentResponse multiFreeBoardCommentResponse = getFreeBoardCommentService.getFreeBoardComment(command);

        //then
        assertThat(multiFreeBoardCommentResponse.getPageInfo().getPageNumber()).isEqualTo(testPageIndex + 1);
        assertThat(multiFreeBoardCommentResponse.getPageInfo().getSize()).isEqualTo(testPageSize);
        assertThat(multiFreeBoardCommentResponse.getPageInfo().getTotalPage()).isEqualTo(testTotalPages);
        assertThat(multiFreeBoardCommentResponse.getPageInfo().getTotalElements()).isEqualTo(testTotalElements);

        List<SingleFreeBoardCommentResponse> responseList = multiFreeBoardCommentResponse.getPageResponse();
        for (int i = 0; i < responseList.size(); i++) {
            assertThat(responseList.get(i).getContent()).isEqualTo(entityResultList.get(i).getContent());
            assertThat(responseList.get(i).getCreatedAt()).isEqualTo(entityResultList.get(i).getCreatedAt());
            assertThat(responseList.get(i).getModifiedAt()).isEqualTo(entityResultList.get(i).getModifiedAt());
            assertThat(responseList.get(i).getWriterInfo().getWriterId()).isEqualTo(entityResultList.get(i).getWriterId());
        }
    }
}