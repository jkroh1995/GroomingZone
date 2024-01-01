package tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command;

import lombok.Getter;

@Getter
public final class DeleteFreeBoardCommentCommand {

    private final long requestMemberId;
    private final long freeBoardId;
    private final long commentId;

    private DeleteFreeBoardCommentCommand(long requestMemberId, long freeBoardId, long commentId) {
        this.requestMemberId = requestMemberId;
        this.freeBoardId = freeBoardId;
        this.commentId = commentId;
    }


    public static DeleteFreeBoardCommentCommand of(long requestMemberId, long freeBoardId, long commentId) {
        return new DeleteFreeBoardCommentCommand(requestMemberId, freeBoardId, commentId);
    }
}
