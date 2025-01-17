package tdd.groomingzone.post.freeboard.dto;

public record GetFreeBoardCommand(
        Long freeBoardId
) {
    public static GetFreeBoardCommand of(Long freeBoardId) {
        return new GetFreeBoardCommand(freeBoardId);
    }
}
