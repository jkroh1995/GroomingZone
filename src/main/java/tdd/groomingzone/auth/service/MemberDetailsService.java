package tdd.groomingzone.auth.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tdd.groomingzone.auth.utils.CustomAuthorityUtils;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;
import tdd.groomingzone.member.adapter.out.persistence.repository.MemberRepository;
import tdd.groomingzone.global.exception.CustomAuthenticationException;
import tdd.groomingzone.global.exception.ExceptionCode;

import java.util.Collection;
import java.util.Optional;

@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final CustomAuthorityUtils customAuthorityUtils;

    public MemberDetailsService(MemberRepository memberRepository, CustomAuthorityUtils customAuthorityUtils) {
        this.memberRepository = memberRepository;
        this.customAuthorityUtils = customAuthorityUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberEntity> member = memberRepository.findByEmail(username);
        MemberEntity findMemberEntity = member.orElseThrow(() -> new CustomAuthenticationException(ExceptionCode.MEMBER_NOT_FOUND));

        return new MemberEntityDetails(findMemberEntity);
    }

    public final class MemberEntityDetails extends MemberEntity implements UserDetails {

        MemberEntityDetails(MemberEntity memberEntity) {
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
