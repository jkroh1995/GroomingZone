package tdd.groomingzone.post.freeboard.application.port.in.command;

import lombok.Data;

@Data
public final class DeleteFreeBoardCommand {
    private final String requestMemberEmail;
    private final long freeBoardId;

    private DeleteFreeBoardCommand(String requestMemberEmail, long freeBoardId) {
        this.requestMemberEmail = requestMemberEmail;
        this.freeBoardId = freeBoardId;
    }

    public static DeleteFreeBoardCommand of(String requestMemberEmail, long freeBoardId){
        return new DeleteFreeBoardCommand(requestMemberEmail, freeBoardId);
    }
}
