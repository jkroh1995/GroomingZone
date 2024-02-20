package tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command;

import lombok.Getter;

@Getter
public final class PutFreeBoardCommentCommand {

    private final String requestMemberEmail;
    private final long boardId;
    private final long commentId;
    private final String content;

    private PutFreeBoardCommentCommand(String requestMemberEmail, long boardId, long commentId, String content) {
        this.requestMemberEmail = requestMemberEmail;
        this.boardId = boardId;
        this.commentId= commentId;
        this.content = content;
    }

    public static PutFreeBoardCommentCommand of(String requestMemberEmail, long boardId, long commentId, String content) {
        return new PutFreeBoardCommentCommand(requestMemberEmail, boardId, commentId, content);
    }
}
