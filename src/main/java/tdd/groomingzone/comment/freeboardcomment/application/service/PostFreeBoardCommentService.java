package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.board.common.WriterInfo;
import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.board.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.SingleFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentEntityResult;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.SaveFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.PostFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase.PostFreeBoardCommentUseCase;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

@Service
public class PostFreeBoardCommentService implements PostFreeBoardCommentUseCase {
    private final LoadMemberPort loadMemberPort;
    private final LoadFreeBoardPort loadFreeBoardPort;
    private final SaveFreeBoardCommentPort saveFreeBoardCommentPort;

    public PostFreeBoardCommentService(LoadMemberPort loadMemberPort, LoadFreeBoardPort loadFreeBoardPort, SaveFreeBoardCommentPort saveFreeBoardCommentPort) {
        this.loadMemberPort = loadMemberPort;
        this.loadFreeBoardPort = loadFreeBoardPort;
        this.saveFreeBoardCommentPort = saveFreeBoardCommentPort;
    }

    @Override
    public SingleFreeBoardCommentResponse postFreeBoardComment(PostFreeBoardCommentCommand postFreeBoardCommentCommand) {
        FreeBoardEntityQueryResult selectFreeBoardQueryResult = loadFreeBoardPort.loadFreeBoardById(postFreeBoardCommentCommand.getBoardId());
        Member freeBoardWriter = loadMemberPort.findMemberById(selectFreeBoardQueryResult.getWriterId());
        Member commentWriter = loadMemberPort.findMemberById(postFreeBoardCommentCommand.getWriterId());
        FreeBoardComment freeBoardComment = FreeBoardCommentPublisher.createFreeBoardComment(selectFreeBoardQueryResult,
                freeBoardWriter,
                commentWriter,
                postFreeBoardCommentCommand.getContent());

        FreeBoardCommentEntityResult saveResult= saveFreeBoardCommentPort.save(freeBoardComment);
        return SingleFreeBoardCommentResponse.of(saveResult.getContent(),
                saveResult.getCreatedAt(),
                saveResult.getModifiedAt(),
                WriterInfo.of(commentWriter));
    }
}
