package tdd.groomingzone.member.domain;

import lombok.Builder;
import lombok.Getter;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

@Getter
public class Member {
    private final long memberId;
    private Email email;
    private Password password;
    private String nickName;
    private PhoneNumber phoneNumber;
    private Role role;

    @Builder
    public Member(long memberId, String email, String password, String nickName, String phoneNumber, String role){
        this.memberId = memberId;
        this.email = Email.of(email);
        this.password = Password.of(password);
        this.nickName = nickName;
        this.phoneNumber = PhoneNumber.of(phoneNumber);
        this.role = Role.valueOf(role);
    }

    public void modify(String email, String password, String nickName, String phoneNumber, String role){
        this.email = Email.of(email);
        this.password = Password.of(password);
        this.nickName = nickName;
        this.phoneNumber = PhoneNumber.of(phoneNumber);
        this.role = Role.valueOf(role);
    }

    public String getEmailValue() {
        return email.getEmail();
    }

    public String getPasswordValue() {
        return password.getPassword();
    }

    public String getRoleValue() {
        return role.getRole();
    }

    public String getPhoneNumberValue() {
        return phoneNumber.getPhoneNumber();
    }

    @Getter
    public enum Role {
        ADMIN("관리자"),
        BARBER("바버"),
        CUSTOMER("고객");

        private final String role;

        Role(String role) {
            this.role = role;
        }
    }
}
