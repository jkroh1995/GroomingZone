package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.post.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.SingleFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.SaveFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.PostFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase.PostFreeBoardCommentUseCase;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;

import java.time.LocalDateTime;

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
    public SingleFreeBoardCommentResponse postFreeBoardComment(PostFreeBoardCommentCommand command) {
        FreeBoard freeBoard = loadFreeBoardPort.loadFreeBoardById(command.boardId());
        Member commentWriter = loadMemberPort.findMemberByEmail(command.writerEmail());

        LocalDateTime writeCommentTime = LocalDateTime.now();
        FreeBoardComment freeBoardComment = FreeBoardComment.builder()
                .writer(commentWriter)
                .freeBoard(freeBoard)
                .content(command.content())
                .createdAt(writeCommentTime)
                .modifiedAt(writeCommentTime)
                .build();

        FreeBoardComment savedComment = saveFreeBoardCommentPort.save(freeBoardComment);

        return SingleFreeBoardCommentResponse.of(savedComment.getContent(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt(),
                WriterInfo.of(commentWriter.getMemberId(), commentWriter.getNickName()));
    }
}
