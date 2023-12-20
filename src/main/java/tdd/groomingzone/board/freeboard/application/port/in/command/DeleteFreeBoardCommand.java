package tdd.groomingzone.board.freeboard.application.port.in.command;

import lombok.Data;

@Data
public final class DeleteFreeBoardCommand {
    private final long requestMemberId;
    private final long freeBoardId;

    private DeleteFreeBoardCommand(long requestMemberId, long freeBoardId) {
        this.requestMemberId = requestMemberId;
        this.freeBoardId = freeBoardId;
    }

    public static DeleteFreeBoardCommand of(long requestMemberId, long freeBoardId){
        return new DeleteFreeBoardCommand(requestMemberId, freeBoardId);
    }
}
