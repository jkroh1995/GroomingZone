package tdd.groomingzone.member.adapter.dto;

import lombok.Builder;

public class MemberApiDto {

    @Builder
    public record Post(
            String email,
            String password,
            String nickName,
            String phoneNumber,
            String role,
            String profileImageUrl
    ) {
    }

    @Builder
    public record Response(
            String email,
            String nickName,
            String phoneNumber,
            String role,
            String profileImageUrl
    ) {
    }
}
