package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.PutFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.SingleFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase.PutFreeBoardCommentUseCase;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.LoadFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.SaveFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

@Service
public class PutFreeBoardCommentService implements PutFreeBoardCommentUseCase {

    private final LoadFreeBoardCommentPort loadFreeBoardCommentPort;
    private final SaveFreeBoardCommentPort saveFreeBoardCommentPort;
    private final LoadMemberPort loadMemberPort;

    public PutFreeBoardCommentService(LoadFreeBoardCommentPort loadFreeBoardCommentPort, SaveFreeBoardCommentPort saveFreeBoardCommentPort, LoadMemberPort loadMemberPort) {
        this.loadFreeBoardCommentPort = loadFreeBoardCommentPort;
        this.saveFreeBoardCommentPort = saveFreeBoardCommentPort;
        this.loadMemberPort = loadMemberPort;
    }

    @Override
    public SingleFreeBoardCommentResponse putFreeBoard(PutFreeBoardCommentCommand command) {
        FreeBoardComment freeBoardComment = loadFreeBoardCommentPort.loadFreeBoardComment(command.getCommentId());
        Member requestMember = loadMemberPort.findMemberByEmail(command.getRequestMemberEmail());
        freeBoardComment.checkMemberAuthority(requestMember);
        freeBoardComment.modify(command.getContent(), LocalDateTime.now());

        FreeBoardComment savedComment = saveFreeBoardCommentPort.save(freeBoardComment);
        return SingleFreeBoardCommentResponse.of(savedComment.getContent(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt(),
                WriterInfo.of(requestMember.getMemberId(), requestMember.getNickName()));
    }
}
