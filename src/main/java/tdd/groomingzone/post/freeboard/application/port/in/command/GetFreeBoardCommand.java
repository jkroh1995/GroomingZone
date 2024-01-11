package tdd.groomingzone.post.freeboard.application.port.in.command;

import lombok.Data;

@Data
public final class GetFreeBoardCommand {
    private final Long freeBoardId;

    private GetFreeBoardCommand(Long freeBoardId) {
        this.freeBoardId = freeBoardId;
    }

    public static GetFreeBoardCommand of(Long freeBoardId){
        return new GetFreeBoardCommand(freeBoardId);
    }
}
