package tdd.groomingzone.auth.application.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tdd.groomingzone.auth.utils.CustomAuthorityUtils;
import tdd.groomingzone.global.exception.CustomAuthenticationException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;
import tdd.groomingzone.member.adapter.out.persistence.repository.MemberEntitiyRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberEntitiyRepository memberEntitiyRepository;
    private final CustomAuthorityUtils customAuthorityUtils;

    public MemberDetailsService(MemberEntitiyRepository memberEntitiyRepository, CustomAuthorityUtils customAuthorityUtils) {
        this.memberEntitiyRepository = memberEntitiyRepository;
        this.customAuthorityUtils = customAuthorityUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberEntity> member = memberEntitiyRepository.findByEmail(username);
        MemberEntity findMemberEntity = member.orElseThrow(() -> new CustomAuthenticationException(ExceptionCode.MEMBER_NOT_FOUND));

        return new MemberDetails(findMemberEntity);
    }

    public final class MemberDetails extends MemberEntity implements UserDetails {

        MemberDetails(MemberEntity memberEntity) {
            setId(memberEntity.getId());
            setEmail(memberEntity.getEmail());
            setNickName(memberEntity.getNickName());
            setPassword(memberEntity.getPassword());
            setRoles(memberEntity.getRoles());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return customAuthorityUtils.createAuthorities(this.getRoles());
        }

        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
