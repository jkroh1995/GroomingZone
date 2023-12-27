package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.board.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;
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
    public void postFreeBoardComment(PostFreeBoardCommentCommand postFreeBoardCommentCommand) {
        FreeBoardEntityQueryResult selectFreeBoardQueryResult = loadFreeBoardPort.loadFreeBoardById(postFreeBoardCommentCommand.getBoardId());
        Member freeBoardWriter = loadMemberPort.findMemberById(selectFreeBoardQueryResult.getWriterId());
        Member commentWriter = loadMemberPort.findMemberById(postFreeBoardCommentCommand.getWriterId());
        FreeBoardComment freeBoardComment = FreeBoardCommentPublisher.createFreeBoardComment(selectFreeBoardQueryResult,
                freeBoardWriter,
                commentWriter,
                postFreeBoardCommentCommand.getContent());

        saveFreeBoardCommentPort.save(freeBoardComment);
    }
}
