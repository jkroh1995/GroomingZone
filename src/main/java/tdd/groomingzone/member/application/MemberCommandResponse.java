package tdd.groomingzone.member.application;

import lombok.Data;

@Data
public final class MemberCommandResponse {
    private final long memberId;
    private final String email;
    private final String nickName;
    private final String phoneNumber;
    private final String role;

    private MemberCommandResponse(long memberId, String email, String nickName, String phoneNumber, String role){
        this.memberId = memberId;
        this.email = email;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public static MemberCommandResponse of(long memberId, String email, String nickName, String phoneNumber, String role){
        return new MemberCommandResponse(memberId, email, nickName, phoneNumber, role);
    }
}
