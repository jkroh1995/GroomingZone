package tdd.groomingzone.comment.freeboardcomment.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.comment.freeboardcomment.dto.PutFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.dto.SingleFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.repository.LoadFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.repository.SaveFreeBoardCommentPort;
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
        FreeBoardComment freeBoardComment = loadFreeBoardCommentPort.loadFreeBoardComment(command.commentId());
        Member requestMember = loadMemberPort.findMemberByEmail(command.requestMemberEmail());
        freeBoardComment.checkMemberAuthority(requestMember);
        freeBoardComment.modify(command.content(), LocalDateTime.now());

        FreeBoardComment savedComment = saveFreeBoardCommentPort.save(freeBoardComment);
        return SingleFreeBoardCommentResponse.of(savedComment.getContent(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt(),
                WriterInfo.of(requestMember.getMemberId(), requestMember.getNickName()));
    }
}
