package singleproject.demo.member.dto;

import lombok.Getter;
import lombok.Setter;
import singleproject.demo.member.entity.Member;

public class MemberDto {

    @Getter
    @Setter
    public static class Post {

        private String name;
        private String email;
        private String nickname;
        private String instagram;
        private String phone;
        private String password;
        private String category;

        public Member postToMember() {
            Member member = new Member();
            member.setName(name);
            member.setEmail(email);
            member.setNickname(nickname);
            member.setInstagram(instagram);
            member.setPhone(phone);
            member.setPassword(password);
            member.setCategory(Member.Category.valueOf(category));

            return member;
        }
    }
}
