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
        FreeBoardCommentPageable freeBoardCommentPageable = new FreeBoardCommentPageable(command.getBoardId(), command.getPageNumber());
        FreeBoardCommentPage freeBoardCommentPage = loadFreeBoardCommentPort.loadFreeBoardCommentPage(freeBoardCommentPageable);

        List<SingleFreeBoardCommentResponse> pageResponse = freeBoardCommentPage.getFreeBoardComments().stream()
                .map(freeBoardComment -> SingleFreeBoardCommentResponse.of(freeBoardComment.getContent(),
                        freeBoardComment.getCreatedAt(),
                        freeBoardComment.getModifiedAt(),
                        WriterInfo.of(freeBoardComment.getWriterId(), freeBoardComment.getWriterNickName())))
                .collect(Collectors.toList());
        PageInfo pageInfo = PageInfo.of(freeBoardCommentPage.getPageNumber(), freeBoardCommentPage.getPageSize(), freeBoardCommentPage.getTotalElements(), freeBoardCommentPage.getTotalPages());

        return MultiFreeBoardCommentResponse.of(pageResponse, pageInfo);
    }
}
