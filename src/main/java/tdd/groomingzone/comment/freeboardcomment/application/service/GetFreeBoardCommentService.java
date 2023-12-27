package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.springframework.stereotype.Service;

import tdd.groomingzone.board.common.WriterInfo;
import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.board.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;
import tdd.groomingzone.comment.common.application.port.in.command.SingleCommentCommandResponse;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.global.pagedresponse.PageInfo;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;

import tdd.groomingzone.comment.common.application.port.in.command.MultiCommentCommandResponse;
import tdd.groomingzone.comment.common.application.port.in.command.GetCommentCommand;
import tdd.groomingzone.comment.common.application.port.in.usecase.GetCommentUseCase;
import tdd.groomingzone.comment.common.application.port.out.CommentPageQueryResult;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.LoadFreeBoardCommentPort;
import tdd.groomingzone.comment.common.application.port.out.CommentPage;
import tdd.groomingzone.member.domain.Member;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetFreeBoardCommentService implements GetCommentUseCase {

    private final LoadFreeBoardCommentPort loadFreeBoardCommentPort;
    private final LoadFreeBoardPort loadFreeBoardPort;
    private final LoadMemberPort loadMemberPort;

    public GetFreeBoardCommentService(LoadFreeBoardCommentPort loadFreeBoardCommentPort, LoadFreeBoardPort loadFreeBoardPort, LoadMemberPort loadMemberPort) {
        this.loadFreeBoardCommentPort = loadFreeBoardCommentPort;
        this.loadFreeBoardPort = loadFreeBoardPort;
        this.loadMemberPort = loadMemberPort;
    }

    @Override
    public MultiCommentCommandResponse getFreeBoardComment(GetCommentCommand command) {
        CommentPage commentPage = new CommentPage(command.getBoardId(), command.getPageNumber());
        CommentPageQueryResult selectQueryResult = loadFreeBoardCommentPort.loadFreeBoardCommentPage(commentPage);

        List<SingleCommentCommandResponse> pageResponse = selectQueryResult.getQueryResults().stream()
                .map(eachQueryResult -> {
                    FreeBoardEntityQueryResult selectFreeBoardQueryResult = loadFreeBoardPort.loadFreeBoardById(eachQueryResult.getBoardId());
                    Member freeBoardWriter = loadMemberPort.findMemberById(selectFreeBoardQueryResult.getWriterId());
                    FreeBoard freeBoard = FreeBoard.builder()
                            .id(selectFreeBoardQueryResult.getId())
                            .writer(freeBoardWriter)
                            .title(selectFreeBoardQueryResult.getTitle())
                            .content(selectFreeBoardQueryResult.getContent())
                            .createdAt(selectFreeBoardQueryResult.getCreatedAt())
                            .modifiedAt(selectFreeBoardQueryResult.getModifiedAt())
                            .build();

                    Member commentWriter = loadMemberPort.findMemberById(eachQueryResult.getWriterId());

                    FreeBoardComment freeBoardComment = FreeBoardComment.builder()
                            .id(eachQueryResult.getCommentId())
                            .writer(commentWriter)
                            .freeBoard(freeBoard)
                            .content(eachQueryResult.getContent())
                            .build();

                    return SingleCommentCommandResponse.of(freeBoardComment.getContent(),
                            freeBoardComment.getCreatedAt(),
                            freeBoardComment.getModifiedAt(),
                            WriterInfo.of(commentWriter));
                })
                .collect(Collectors.toList());
        PageInfo pageInfo = PageInfo.of(selectQueryResult.getPageNumber() + 1, selectQueryResult.getPageSize(), selectQueryResult.getTotalElements(), selectQueryResult.getTotalPages());

        return MultiCommentCommandResponse.of(pageResponse, pageInfo);
    }
}
