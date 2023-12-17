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
    }

    @Builder
    public static class Response {
        public String email;
        public String nickName;
        public String phoneNumber;
        public String role;
    }
}
