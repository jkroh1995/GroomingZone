package tdd.groomingzone.domain.member.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    @DisplayName("게시글 작성 시 자신의 게시글 리스트에 게시글이 등록되며, 게시글에 자신이 작성자로 등록되야 한다")
    void testWriteFreeBoard(){
        Member member = createMember();

        FreeBoard freeBoard = FreeBoard.builder()
                .title("test")
                .content("content")
                .build();

        member.writeFreeBoard(freeBoard);

        assertThat(freeBoard.getWriter()).isEqualTo(member);
        assertThat(member.getFreeBoards().get(0)).isEqualTo(freeBoard);
    }

    private Member createMember() {
        return Member.builder()
                .email("jk@gmail.com")
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123"))
                .name("JKROH")
                .phoneNumber("010-1111-2222")
                .roles(List.of("BARBER", "CUSTOMER"))
                .build();
    }
}