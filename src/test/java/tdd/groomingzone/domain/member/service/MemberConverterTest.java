package tdd.groomingzone.domain.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import tdd.groomingzone.domain.board.freeboard.dto.FreeBoardDto;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.service.FreeBoardConverter;
import tdd.groomingzone.domain.member.dto.MemberDto;
import tdd.groomingzone.domain.member.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberConverterTest {

    private final MemberConverter memberConverter = new MemberConverter();
    private final MemberRolesGenerator memberRolesGenerator = new MemberRolesGenerator("admin@admin.com");

    @Test
    @DisplayName("Post DTO를 적절한 Entity로 변환한다.")
    void testConvertPostDtoToEntityTest() {
        MemberDto.Post postDto = new MemberDto.Post();
        String email = "email";
        String password = "password";
        String name = "name";
        String phoneNumber = "010-1111-1111";
        String role = "바버";

        postDto.email = email;
        postDto.password = password;
        postDto.name = name;
        postDto.phoneNumber = phoneNumber;
        postDto.role = role;

        Member entity = memberConverter.convertPostDtoToEntity(postDto, memberRolesGenerator.generateMemberRoles(email, role));

        assertThat(entity.getEmail()).isEqualTo(postDto.email);
        assertThat(entity.getPassword()).isEqualTo(postDto.password);
        assertThat(entity.getName()).isEqualTo(postDto.name);
        assertThat(entity.getPhoneNumber()).isEqualTo(postDto.phoneNumber);
        assertThat(entity.getRoles()).contains("BARBER");
    }

    @Test
    @DisplayName("Entity를 적절한 Response DTO로 변환한다.")
    void testConvertEntityToResponseDto() {
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

        MemberDto.Response response = memberConverter.convertEntityToResponseDto(member);

        assertThat(response.email).isEqualTo(member.getEmail());
        assertThat(response.name).isEqualTo(member.getName());
        assertThat(response.role).isEqualTo(member.getRoles().get(0));
        assertThat(response.phoneNumber).isEqualTo(member.getPhoneNumber());
    }
}