package tdd.groomingzone.member.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;
import tdd.groomingzone.member.application.port.in.MemberCommandResponse;
import tdd.groomingzone.member.application.port.in.command.PostMemberCommand;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.application.port.out.SaveMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.util.MemberCreator;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostMemberServiceTest {

    @Mock
    private SaveMemberPort saveMemberPort;

    @Mock
    private LoadMemberPort loadMemberPort;

    @InjectMocks
    private PostMemberService postMemberService;

    @Test
    @DisplayName("회원을 저장한다.")
    void testCreateMember() {
        //given
        given(loadMemberPort.findOptionalMemberByEmail(anyString())).willReturn(Optional.empty());

        String email = "email@email.com";
        String password = "11aA!@Password";
        String nickName = "nickName";
        String phoneNumber = "010-1111-1111";
        String role = "BARBER";
        String profileImageUrl = "sample url";

        Member member = MemberCreator.createMember();

        given(saveMemberPort.save(any())).willReturn(member);

        PostMemberCommand command = PostMemberCommand.of(email, password, nickName, phoneNumber, role, profileImageUrl);

        //when
        MemberCommandResponse memberCommandResponse = postMemberService.postMember(command);

        assertThat(memberCommandResponse.memberId()).isEqualTo(member.getMemberId());
        assertThat(memberCommandResponse.email()).isEqualTo(member.getEmail());
        assertThat(memberCommandResponse.nickName()).isEqualTo(member.getNickName());
        assertThat(memberCommandResponse.phoneNumber()).isEqualTo(member.getPhoneNumber());
        assertThat(memberCommandResponse.role()).isEqualTo(member.getRole());
    }

    @Test
    @DisplayName("중복된 이메일로 회원 가입을 시도하면 예외가 발생한다.")
    void testDuplicateEmail() {
        // given
        MemberEntity memberEntity = MemberEntity.builder().build();

        given(loadMemberPort.findOptionalMemberByEmail(anyString())).willReturn(Optional.of(memberEntity));

        String email = "email@email.com";
        String password = "11aA!@Password";
        String nickName = "nickName";
        String phoneNumber = "010-1111-1111";
        String role = "BARBER";
        String profileImageUrl = "sample url";

        PostMemberCommand command = PostMemberCommand.of(email, password, nickName, phoneNumber, role, profileImageUrl);

        //when, then
        assertThrows(BusinessException.class, () -> postMemberService.postMember(command));
    }
}