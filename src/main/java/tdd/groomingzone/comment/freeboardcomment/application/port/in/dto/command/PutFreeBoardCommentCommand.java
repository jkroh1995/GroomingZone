package tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command;

import lombok.Getter;

@Getter
public final class PutFreeBoardCommentCommand {

    private final long requestMemberId;
    private final long boardId;
    private final long commentId;
    private final String content;

    private PutFreeBoardCommentCommand(long requestMemberId, long boardId, long commentId, String content) {
        this.requestMemberId = requestMemberId;
        this.boardId = boardId;
        this.commentId= commentId;
        this.content = content;
    }

    public static PutFreeBoardCommentCommand of(long requestMemberId, long boardId, long commentId, String content) {
        return new PutFreeBoardCommentCommand(requestMemberId, boardId, commentId, content);
    }
}
