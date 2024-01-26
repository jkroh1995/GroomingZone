package tdd.groomingzone.member.adapter.dto;

import lombok.Builder;
import lombok.Getter;

public class MemberApiDto {

    @Builder
    @Getter
    public static class Post {
        private String email;
        private String password;
        private String nickName;
        private String phoneNumber;
        private String role;
        private String profileImageUrl;
    }

    @Builder
    @Getter
    public static class Response {
        private final String email;
        private final String nickName;
        private final String phoneNumber;
        private final String role;
        private final String profileImageUrl;
    }
}
