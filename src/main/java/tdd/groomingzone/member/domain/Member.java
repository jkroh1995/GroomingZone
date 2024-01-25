package tdd.groomingzone.member.domain;

import lombok.Builder;
import lombok.Getter;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

import java.time.LocalDateTime;

@Getter
public class Member {
    private final long memberId;
    private Email email;
    private Password password;
    private String nickName;
    private PhoneNumber phoneNumber;
    private Role role;
    private final Provider provider;
    private final LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    private Member(long memberId, String email, String password, String nickName, String phoneNumber, String role, String provider, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.memberId = memberId;
        this.email = Email.of(email);
        this.password = Password.of(password);
        this.nickName = nickName;
        this.phoneNumber = PhoneNumber.of(phoneNumber);
        this.role = Role.of(role);
        this.provider = Provider.of(provider);
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public void modify(String email, String password, String nickName, String phoneNumber, String role, LocalDateTime modifiedAt) {
        this.email = Email.of(email);
        this.password = Password.of(password);
        this.nickName = nickName;
        this.phoneNumber = PhoneNumber.of(phoneNumber);
        validateRole(role);
        this.role = Role.of(role);
        this.modifiedAt = modifiedAt;
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

    public String getProvider(){
        return this.provider.getProvider();
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

    @Getter
    public enum Provider{
        SERVER("SERVER"),
        KAKAO("KAKAO"),
        NAVER("NAVER");

        private final String provider;

        Provider(String provider) {
            this.provider = provider;
        }

        public static Provider of(String input){
            try{
                return Provider.valueOf(input);
            }catch (IllegalArgumentException e){
                throw new BusinessException(ExceptionCode.INVALID_ROLE);
            }
        }
    }
}
