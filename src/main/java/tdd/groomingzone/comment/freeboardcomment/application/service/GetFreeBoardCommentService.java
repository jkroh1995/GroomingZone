package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.springframework.stereotype.Service;

import tdd.groomingzone.comment.freeboardcomment.adapter.out.FreeBoardCommentPage;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.SingleFreeBoardCommentResponse;
import tdd.groomingzone.global.pagedresponse.PageInfo;

import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.MultiFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.GetFreeBoardCommentPageCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase.GetFreeBoardCommentUseCase;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.LoadFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentPageable;
import tdd.groomingzone.post.freeboard.application.service.PageNumber;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetFreeBoardCommentService implements GetFreeBoardCommentUseCase {

    private final LoadFreeBoardCommentPort loadFreeBoardCommentPort;

    public GetFreeBoardCommentService(LoadFreeBoardCommentPort loadFreeBoardCommentPort) {
        this.loadFreeBoardCommentPort = loadFreeBoardCommentPort;
    }

    @Override
    public MultiFreeBoardCommentResponse getFreeBoardComment(GetFreeBoardCommentPageCommand command) {
        FreeBoardCommentPageable freeBoardCommentPageable = FreeBoardCommentPageable.of(command.boardId(), new PageNumber(command.pageNumber()));
        FreeBoardCommentPage freeBoardCommentPage = loadFreeBoardCommentPort.loadFreeBoardCommentPage(freeBoardCommentPageable);

        List<SingleFreeBoardCommentResponse> pageResponse = freeBoardCommentPage.freeBoardComments().stream()
                .map(freeBoardComment -> SingleFreeBoardCommentResponse.of(freeBoardComment.getContent(),
                        freeBoardComment.getCreatedAt(),
                        freeBoardComment.getModifiedAt(),
                        WriterInfo.of(freeBoardComment.getWriterId(), freeBoardComment.getWriterNickName())))
                .collect(Collectors.toList());
        PageInfo pageInfo = PageInfo.of(freeBoardCommentPage.pageNumber(), freeBoardCommentPage.pageSize(), freeBoardCommentPage.totalElements(), freeBoardCommentPage.totalPages());

        return MultiFreeBoardCommentResponse.of(pageResponse, pageInfo);
    }
}
