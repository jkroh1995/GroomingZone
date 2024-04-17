package tdd.groomingzone.member.application.port.in;

public record MemberCommandResponse(
        long memberId,
        String email,
        String nickName,
        String phoneNumber,
        String role,
        String provider,
        String profileImageUrl

) {
    public static MemberCommandResponse of(long memberId, String email, String nickName, String phoneNumber, String role, String provider, String profileImageUrl){
        return new MemberCommandResponse(memberId, email, nickName, phoneNumber, role, provider, profileImageUrl);
    }
}
