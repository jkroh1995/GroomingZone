package tdd.groomingzone.domain.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.domain.member.entity.Member;
import tdd.groomingzone.domain.member.repository.MemberRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberCommandServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberCommandService memberCommandService;

    @Test
    @DisplayName("회원을 저장한다.")
    void testCreateMember(){
        String email = "email";
        String password = "password";
        String name = "name";
        String phoneNumber = "010-1111-1111";
        List<String> roles = List.of("BARBER", "CUSTOMER");

        Member member = Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .roles(roles)
                .build();

        given(memberRepository.save(any())).willReturn(member);

        Member savedMember = memberCommandService.createMember(member);

        assertThat(savedMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(savedMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(savedMember.getName()).isEqualTo(member.getName());
        assertThat(savedMember.getPhoneNumber()).isEqualTo(member.getPhoneNumber());
        assertThat(savedMember.getRoles()).isEqualTo(member.getRoles());
    }
}