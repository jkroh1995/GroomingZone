package tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command;

import lombok.Getter;

@Getter
public final class DeleteFreeBoardCommentCommand {

    private final String requestMemberEmail;
    private final long freeBoardId;
    private final long commentId;

    private DeleteFreeBoardCommentCommand(String requestMemberEmail, long freeBoardId, long commentId) {
        this.requestMemberEmail = requestMemberEmail;
        this.freeBoardId = freeBoardId;
        this.commentId = commentId;
    }


    public static DeleteFreeBoardCommentCommand of(String requestMemberEmail, long freeBoardId, long commentId) {
        return new DeleteFreeBoardCommentCommand(requestMemberEmail, freeBoardId, commentId);
    }
}
