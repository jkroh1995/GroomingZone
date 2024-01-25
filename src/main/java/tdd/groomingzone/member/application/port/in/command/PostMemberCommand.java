package tdd.groomingzone.member.application.port.in.command;

import lombok.Data;

@Data
public final class PostMemberCommand {

    private final String email;
    private final String password;
    private final String nickName;
    private final String phoneNumber;
    private final String role;
    private final String profileImageUrl;

    private PostMemberCommand(String email, String password, String nickName, String phoneNumber, String role, String profileImageUrl){
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.profileImageUrl = profileImageUrl;
    }

    public static PostMemberCommand of(String email, String password, String nickName, String phoneNumber, String role, String profileImageUrl){
        return new PostMemberCommand(email, password, nickName, phoneNumber, role, profileImageUrl);
    }
}
