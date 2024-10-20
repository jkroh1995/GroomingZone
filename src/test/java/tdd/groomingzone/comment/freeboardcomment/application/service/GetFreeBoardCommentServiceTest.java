package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import tdd.groomingzone.comment.common.CommentEntity;
import tdd.groomingzone.comment.freeboardcomment.repository.FreeBoardCommentPage;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.comment.freeboardcomment.service.GetFreeBoardCommentService;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;
import tdd.groomingzone.comment.freeboardcomment.dto.GetFreeBoardCommentPageCommand;
import tdd.groomingzone.comment.freeboardcomment.dto.MultiFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.dto.SingleFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.repository.LoadFreeBoardCommentPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.util.MemberCreator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetFreeBoardCommentServiceTest {

    @Mock
    private LoadFreeBoardCommentPort loadFreeBoardCommentPort;

    @InjectMocks
    private GetFreeBoardCommentService getFreeBoardCommentService;

    @Test
    void testGetFreeBoardComment() {
        //given
        long testBoardId = 1L;
        int testPageNumber = 1;
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();

        GetFreeBoardCommentPageCommand command = GetFreeBoardCommentPageCommand.of(testBoardId, testPageNumber);

        Member writer = MemberCreator.createMember();
        FreeBoard freeBoard = FreeBoard.builder()
                .writer(writer)
                .id(testBoardId)
                .title("title")
                .content("content")
                .viewCount(1)
                .createdAt(testCreatedAt)
                .modifiedAt(testCreatedAt)
                .build();

        List<FreeBoardComment> entityResultList = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            entityResultList.add(FreeBoardComment.builder()
                            .freeBoard(freeBoard)
                            .writer(writer)
                            .id(i)
                            .content("content")
                            .createdAt(testCreatedAt)
                            .modifiedAt(testModifiedAt)
                            .build());
        }

        List<CommentEntity> entityList = entityResultList.stream()
                .map(entity -> CommentEntity.builder()
                        .boardId(entity.getBoardId())
                        .writerId(entity.getWriterId())
                        .content(entity.getContent())
                        .createdAt(entity.getCreatedAt())
                        .modifiedAt(entity.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
        Page<CommentEntity> commentEntityPage = new PageImpl<>(entityList);
        FreeBoardCommentPage commentPageResult = FreeBoardCommentPage.of(entityResultList, commentEntityPage);
        given(loadFreeBoardCommentPort.loadFreeBoardCommentPage(any())).willReturn(commentPageResult);

        //when
        MultiFreeBoardCommentResponse multiFreeBoardCommentResponse = getFreeBoardCommentService.getFreeBoardComment(command);

        //then
        assertThat(multiFreeBoardCommentResponse.pageInfo().getPageNumber()).isEqualTo(commentEntityPage.getNumber() + 1);
        assertThat(multiFreeBoardCommentResponse.pageInfo().getSize()).isEqualTo(commentEntityPage.getSize());
        assertThat(multiFreeBoardCommentResponse.pageInfo().getTotalPage()).isEqualTo(commentEntityPage.getTotalPages());
        assertThat(multiFreeBoardCommentResponse.pageInfo().getTotalElements()).isEqualTo(commentEntityPage.getTotalElements());

        List<SingleFreeBoardCommentResponse> responseList = multiFreeBoardCommentResponse.pageResponse();
        for (int i = 0; i < responseList.size(); i++) {
            assertThat(responseList.get(i).content()).isEqualTo(entityResultList.get(i).getContent());
            assertThat(responseList.get(i).createdAt()).isEqualTo(entityResultList.get(i).getCreatedAt());
            assertThat(responseList.get(i).modifiedAt()).isEqualTo(entityResultList.get(i).getModifiedAt());
            assertThat(responseList.get(i).writerInfo().getWriterId()).isEqualTo(entityResultList.get(i).getWriterId());
        }
    }
}