package tdd.groomingzone.domain.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.domain.member.entity.Member;
import tdd.groomingzone.domain.member.repository.MemberRepository;
import tdd.groomingzone.global.exception.BusinessException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberQueryServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberQueryService memberQueryService;

    @Test
    @DisplayName("이메일을 통해 회원을 찾는다.")
    void testReadMemberByEmail(){

        String appropriateEmail = "test@test.com";
        String inappropriateEmail = "no@no.com";

        String email = "email";
        String password = "password";
        String name = "name";
        String phoneNumber = "010-1111-1111";
        List<String> roles = List.of("BARBER", "CUSTOMER");

        Optional<Member> testEntity = Optional.of(Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .roles(roles)
                .build());

        given(memberRepository.findByEmail(appropriateEmail)).willReturn(testEntity);
        given(memberRepository.findByEmail(inappropriateEmail)).willReturn(Optional.empty());

        Member findMember = memberQueryService.readMemberByEmail(appropriateEmail);

        assertThat(findMember).isEqualTo(testEntity.get());
        assertThrows(BusinessException.class, () -> memberQueryService.readMemberByEmail(inappropriateEmail));
    }
}