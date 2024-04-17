package tdd.groomingzone.post.freeboard.application.port.in.command;

public record DeleteFreeBoardCommand (
        String requestMemberEmail,
        Long freeBoardId
){
    public static DeleteFreeBoardCommand of(String requestMemberEmail, long freeBoardId){
        return new DeleteFreeBoardCommand(requestMemberEmail, freeBoardId);
    }
}
