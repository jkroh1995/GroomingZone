package tdd.groomingzone.member.application.port.in;

import lombok.Data;

@Data
public final class MemberCommandResponse {
    private final long memberId;
    private final String email;
    private final String nickName;
    private final String phoneNumber;
    private final String role;
    private final String provider;
    private final String profileImageUrl;

    private MemberCommandResponse(long memberId, String email, String nickName, String phoneNumber, String role, String provider, String profileImageUrl){
        this.memberId = memberId;
        this.email = email;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.provider = provider;
        this.profileImageUrl = profileImageUrl;
    }

    public static MemberCommandResponse of(long memberId, String email, String nickName, String phoneNumber, String role, String provider, String profileImageUrl){
        return new MemberCommandResponse(memberId, email, nickName, phoneNumber, role, provider, profileImageUrl);
    }
}
