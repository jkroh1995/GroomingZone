package tdd.groomingzone.auth.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import tdd.groomingzone.auth.utils.CustomAuthorityUtils;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntityRolesGenerator;
import tdd.groomingzone.member.adapter.out.persistence.repository.MemberEntitiyRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OAuth2MemberService extends DefaultOAuth2UserService {

    private final MemberEntityRolesGenerator rolesGenerator;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberEntitiyRepository memberEntitiyRepository;

    public OAuth2MemberService(MemberEntityRolesGenerator rolesGenerator, CustomAuthorityUtils authorityUtils, MemberEntitiyRepository memberEntitiyRepository) {
        this.rolesGenerator = rolesGenerator;
        this.authorityUtils = authorityUtils;
        this.memberEntitiyRepository = memberEntitiyRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> userAttributes = oAuth2User.getAttributes();

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userAttributes);

        String email = attributes.getEmail();
        List<String> roles = rolesGenerator.generateMemberRoles(email, "고객");
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities(roles);

        saveMemberIfNotPresent(email, attributes.getName(), roles);
        return new CustomOAuth2User(registrationId, userAttributes, authorities, email);
    }

    private void saveMemberIfNotPresent(String email, String nickName, List<String> roles){
        if(memberEntitiyRepository.findByEmail(email).isEmpty()){
            LocalDateTime createTime = LocalDateTime.now();
            MemberEntity member = MemberEntity.builder()
                    .id(0)
                    .email(email)
                    .nickName(nickName)
                    .roles(roles)
                    .createdAt(createTime)
                    .modifiedAt(createTime)
                    .build();
            memberEntitiyRepository.save(member);
        }
    }
}
