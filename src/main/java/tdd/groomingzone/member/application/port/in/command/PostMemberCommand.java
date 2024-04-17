package tdd.groomingzone.member.application.port.in.command;

public record PostMemberCommand (
        String email,
        String password,
        String nickName,
        String phoneNumber,
        String role,
        String profileImageUrl
){
    public static PostMemberCommand of(String email, String password, String nickName, String phoneNumber, String role, String profileImageUrl){
        return new PostMemberCommand(email, password, nickName, phoneNumber, role, profileImageUrl);
    }
}
