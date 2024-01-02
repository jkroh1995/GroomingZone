package tdd.groomingzone.member.adapter.out.persistence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tdd.groomingzone.member.domain.Member;

@Component
public class MemberMapper {

    @Value("${member.temporary.password}")
    private String MEMBER_DEFAULT_PASSWORD;
    private final PasswordEncoder passwordEncoder;
    private final MemberEntityRolesGenerator memberEntityRolesGenerator;

    public MemberMapper(PasswordEncoder passwordEncoder, MemberEntityRolesGenerator memberEntityRolesGenerator) {
        this.passwordEncoder = passwordEncoder;
        this.memberEntityRolesGenerator = memberEntityRolesGenerator;
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
                .email(member.getEmail())
                .nickName(member.getNickName())
                .password(passwordEncoder.encode(member.getPassword()))
                .roles(memberEntityRolesGenerator.generateMemberRoles(member.getEmail(), member.getRole()))
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
