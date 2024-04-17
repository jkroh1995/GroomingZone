package tdd.groomingzone.post.freeboard.application.port.in.command;

public record GetFreeBoardCommand(
        Long freeBoardId
) {
    public static GetFreeBoardCommand of(Long freeBoardId) {
        return new GetFreeBoardCommand(freeBoardId);
    }
}
