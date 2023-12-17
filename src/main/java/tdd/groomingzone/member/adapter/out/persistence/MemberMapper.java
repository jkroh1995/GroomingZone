package tdd.groomingzone.member.adapter.out.persistence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tdd.groomingzone.member.application.MemberRolesGenerator;
import tdd.groomingzone.member.domain.Member;

@Component
public class MemberMapper {

    @Value("${member.default.password}")
    private String MEMBER_DEFAULT_PASSWORD;
    private final PasswordEncoder passwordEncoder;
    private final MemberRolesGenerator memberRolesGenerator;

    public MemberMapper(PasswordEncoder passwordEncoder, MemberRolesGenerator memberRolesGenerator) {
        this.passwordEncoder = passwordEncoder;
        this.memberRolesGenerator = memberRolesGenerator;
    }

    public Member mapToDomainEntity(MemberEntity findMember) {
        return Member.builder()
                .memberId(findMember.getId())
                .email(findMember.getEmail())
                .password(MEMBER_DEFAULT_PASSWORD)
                .nickName(findMember.getNickName())
                .phoneNumber(findMember.getPhoneNumber())
                .role(findMember.getRoles().get(0))
                .build();
    }

    public MemberEntity mapToDatabaseEntity(Member member) {
        return MemberEntity.builder()
                .id(member.getMemberId())
                .email(member.getEmailValue())
                .nickName(member.getNickName())
                .password(passwordEncoder.encode(member.getPasswordValue()))
                .roles(memberRolesGenerator.generateMemberRoles(member.getEmailValue(), member.getRoleValue()))
                .phoneNumber(member.getPhoneNumberValue())
                .build();
    }
}
