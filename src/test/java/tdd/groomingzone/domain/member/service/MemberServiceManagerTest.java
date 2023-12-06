package tdd.groomingzone.domain.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import tdd.groomingzone.domain.auth.utils.CustomAuthorityUtils;
import tdd.groomingzone.domain.member.dto.MemberDto;
import tdd.groomingzone.domain.member.entity.Member;
import tdd.groomingzone.global.exception.BusinessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceManagerTest {

    @Mock
    private MemberCommandService memberCommandService;

    @Mock
    private MemberQueryService memberQueryService;

    @Mock
    private MemberConverter memberConverter;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CustomAuthorityUtils customAuthorityUtils;

    @InjectMocks
    private MemberServiceManager memberService;

    @Test
    @DisplayName("중복된 이메일로 회원 가입을 시도하면 예외가 발생한다.")
    void testDuplicateEmail(){
        // given
        Member member = Member.builder().build();
        MemberDto.Post dto = new MemberDto.Post();
        dto.email = "duplicate@dup.com";

        given(memberQueryService.readMemberByEmail(anyString())).willReturn(member);

        //when, then
        assertThrows(BusinessException.class, () -> memberService.postMember(dto));
    }
}