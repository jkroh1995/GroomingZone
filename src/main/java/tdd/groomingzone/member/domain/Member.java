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
    private Member(long memberId, String email, String password, String nickName, String phoneNumber, String role) {
        this.memberId = memberId;
        this.email = Email.of(email);
        this.password = Password.of(password);
        this.nickName = nickName;
        this.phoneNumber = PhoneNumber.of(phoneNumber);
        this.role = Role.of(role);
    }

    public void modify(String email, String password, String nickName, String phoneNumber, String role) {
        this.email = Email.of(email);
        this.password = Password.of(password);
        this.nickName = nickName;
        this.phoneNumber = PhoneNumber.of(phoneNumber);
        validateRole(role);
        this.role = Role.valueOf(role);
    }

    private void validateRole(String inputRole) {
        Role [] roles = Role.values();
        for(Role role : roles){
            if(role.getRole().equals(inputRole)){
                return;
            }
        }
        throw new BusinessException(ExceptionCode.INVALID_ROLE);
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getPassword() {
        return password.getPassword();
    }

    public String getRole() {
        return role.getRole();
    }

    public String getPhoneNumber() {
        return phoneNumber.getPhoneNumber();
    }

    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
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

        public static Role of(String inputRole){
            try{
                return Role.valueOf(inputRole);
            }catch (IllegalArgumentException e){
                throw new BusinessException(ExceptionCode.INVALID_ROLE);
            }
        }
    }
}
