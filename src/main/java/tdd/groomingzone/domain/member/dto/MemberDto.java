package tdd.groomingzone.domain.member.dto;

import lombok.Builder;

public class MemberDto {
    public static class Post {
        public String email;
        public String password;
        public String name;
        public String phoneNumber;
        public String role;
    }

    @Builder
    public static class Response {
        public String email;
        public String name;
        public String phoneNumber;
        public String role;
    }
}
