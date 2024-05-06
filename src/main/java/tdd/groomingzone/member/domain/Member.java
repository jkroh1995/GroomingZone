package tdd.groomingzone.member.domain;

import lombok.Builder;
import lombok.Getter;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

import java.time.LocalDateTime;

@Getter
public class Member {
    private final MemberVO memberVO;
    private Email email;
    private Password password;
    private NickName nickName;
    private PhoneNumber phoneNumber;
    private Role role;
    private final Provider provider;
    private final LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String profileImageUrl;

    @Builder
    private Member(long memberId, String email, String password, String nickName, String phoneNumber, String role, String provider, LocalDateTime createdAt, LocalDateTime modifiedAt, String profileImageUrl) {
        this.memberVO = new MemberVO(memberId);
        this.email = new Email(email);
        this.password = new Password(password);
        this.nickName = new NickName(nickName);
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.role = Role.of(role);
        this.provider = Provider.of(provider);
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.profileImageUrl = profileImageUrl;
    }

    public void modify(String email, String password, String nickName, String phoneNumber, String role, LocalDateTime modifiedAt, String profileImageUrl) {
        this.email = new Email(email);
        this.password = new Password(password);
        this.nickName = new NickName(nickName);
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.role = Role.of(role);
        this.modifiedAt = modifiedAt;
        this.profileImageUrl = profileImageUrl;
    }

    public String getNickName(){
        return nickName.getValue();
    }

    public String getEmail() {
        return email.email();
    }

    public String getPassword() {
        return password.password();
    }

    public String getRole() {
        return role.getRole();
    }

    public String getPhoneNumber() {
        return phoneNumber.phoneNumber();
    }

    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
    }

    public String getProvider(){
        return this.provider.getProvider();
    }

    public Long getMemberId() {
        return memberVO.memberId();
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
                validateRole(inputRole);
                return Role.valueOf(inputRole);
            }catch (IllegalArgumentException e){
                throw new BusinessException(ExceptionCode.INVALID_ROLE);
            }
        }

        private static void validateRole(String inputRole) {
            Role [] roles = Role.values();
            for(Role role : roles){
                if(role.getRole().equals(inputRole)){
                    return;
                }
            }
            throw new BusinessException(ExceptionCode.INVALID_ROLE);
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
