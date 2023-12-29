package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.springframework.stereotype.Service;

import tdd.groomingzone.board.common.WriterInfo;
import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.board.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.SingleFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.global.pagedresponse.PageInfo;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;

import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.MultiFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.GetFreeBoardCommentPageCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase.GetFreeBoardCommentUseCase;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentPageResult;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.LoadFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentPageable;
import tdd.groomingzone.member.domain.Member;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetFreeBoardCommentService implements GetFreeBoardCommentUseCase {

    private final LoadFreeBoardCommentPort loadFreeBoardCommentPort;
    private final LoadFreeBoardPort loadFreeBoardPort;
    private final LoadMemberPort loadMemberPort;

    public GetFreeBoardCommentService(LoadFreeBoardCommentPort loadFreeBoardCommentPort, LoadFreeBoardPort loadFreeBoardPort, LoadMemberPort loadMemberPort) {
        this.loadFreeBoardCommentPort = loadFreeBoardCommentPort;
        this.loadFreeBoardPort = loadFreeBoardPort;
        this.loadMemberPort = loadMemberPort;
    }

    @Override
    public MultiFreeBoardCommentResponse getFreeBoardComment(GetFreeBoardCommentPageCommand command) {
        FreeBoardCommentPageable freeBoardCommentPageable = new FreeBoardCommentPageable(command.getBoardId(), command.getPageNumber());
        FreeBoardCommentPageResult selectQueryResult = loadFreeBoardCommentPort.loadFreeBoardCommentPage(freeBoardCommentPageable);

        List<SingleFreeBoardCommentResponse> pageResponse = selectQueryResult.getQueryResults().stream()
                .map(eachQueryResult -> {
                    FreeBoardEntityQueryResult selectFreeBoardQueryResult = loadFreeBoardPort.loadFreeBoardById(eachQueryResult.getBoardId());
                    Member freeBoardWriter = loadMemberPort.findMemberById(selectFreeBoardQueryResult.getWriterId());
                    Member commentWriter = loadMemberPort.findMemberById(eachQueryResult.getWriterId());
                    FreeBoardComment freeBoardComment = FreeBoardCommentPublisher.createFreeBoardComment(selectFreeBoardQueryResult,
                            freeBoardWriter,
                            commentWriter,
                            eachQueryResult.getContent());

                    return SingleFreeBoardCommentResponse.of(freeBoardComment.getContent(),
                            freeBoardComment.getCreatedAt(),
                            freeBoardComment.getModifiedAt(),
                            WriterInfo.of(commentWriter));
                })
                .collect(Collectors.toList());
        PageInfo pageInfo = PageInfo.of(selectQueryResult.getPageNumber() + 1, selectQueryResult.getPageSize(), selectQueryResult.getTotalElements(), selectQueryResult.getTotalPages());

        return MultiFreeBoardCommentResponse.of(pageResponse, pageInfo);
    }
}