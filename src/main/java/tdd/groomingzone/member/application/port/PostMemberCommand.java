package tdd.groomingzone.member.application.port;

import lombok.Data;

@Data
public final class PostMemberCommand {

    private final String email;
    private final String password;
    private final String nickName;
    private final String phoneNumber;
    private final String role;

    private PostMemberCommand(String email, String password, String nickName, String phoneNumber, String role){
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public static PostMemberCommand of(String email, String password, String nickName, String phoneNumber, String role){
        return new PostMemberCommand(email, password, nickName, phoneNumber, role);
    }
}
