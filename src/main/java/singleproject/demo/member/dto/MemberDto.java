package singleproject.demo.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import singleproject.demo.member.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberDto {

    @Getter
    public static class Post {

        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email
        private String email;

        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
        private String phone;

        public Member memberPostDtoToMember() {
            Member member = new Member();
            member.setName(name);
            member.setEmail(email);
            member.setPhone(phone);
            return member;
        }
    }

    @Getter
    @Setter
    public static class Response {

        long memberId;
        String name;
        String email;
        String phone;
    }


}
