package tdd.groomingzone.post.freeboard.domain;

import org.junit.jupiter.api.Test;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.util.StubTime;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FreeBoardTest {

    @Test
    void testModify() {
        Member writer = Member.builder()
                .memberId(1L)
                .email("test@email.com")
                .password("11aA!!@@Password")
                .phoneNumber("010-1111-1111")
                .nickName("nickName")
                .role("BARBER")
                .build();

        FreeBoard testEntity = FreeBoard.builder()
                .id(1L)
                .writer(writer)
                .title("test")
                .content("content")
                .viewCount(0)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        String modifiedTitle = "modifiedTitle";
        String modifiedContent = "modifiedContent";
        LocalDateTime fakeModifiedTime = LocalDateTime.of(2099, 11, 28, 22, 30, 10);
        StubTime stubTime = new StubTime(fakeModifiedTime);

        testEntity.modify(modifiedTitle, modifiedContent, stubTime.now());
        assertThat(testEntity.getTitle()).isEqualTo(modifiedTitle);
        assertThat(testEntity.getContent()).isEqualTo(modifiedContent);
        assertThat(testEntity.getModifiedAt()).isEqualTo(fakeModifiedTime);
    }

    @Test
    void testViewed() {
        Member writer = Member.builder()
                .memberId(1L)
                .email("test@email.com")
                .password("11aA!!@@Password")
                .phoneNumber("010-1111-1111")
                .nickName("nickName")
                .role("BARBER")
                .build();

        int viewCount = 0;

        FreeBoard testEntity = FreeBoard.builder()
                .id(1L)
                .writer(writer)
                .title("test")
                .content("content")
                .viewCount(viewCount)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        testEntity.viewed();

        assertThat(testEntity.getViewCount()).isEqualTo(viewCount + 1);
    }

    @Test
    void testCheckMemberAuthority() {
        Member writer = Member.builder()
                .memberId(1L)
                .email("test@email.com")
                .password("11aA!!@@Password")
                .phoneNumber("010-1111-1111")
                .nickName("nickName")
                .role("BARBER")
                .build();

        FreeBoard testEntity = FreeBoard.builder()
                .id(1L)
                .writer(writer)
                .title("test")
                .content("content")
                .viewCount(0)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        Member otherMember = Member.builder()
                .memberId(2L)
                .email("test1@email.com")
                .password("11aA!!2@@Pasrd")
                .phoneNumber("010-2211-1111")
                .nickName("nickName22")
                .role("CUSTOMER")
                .build();

        assertThrows(BusinessException.class,() -> testEntity.checkMemberAuthority(otherMember));
    }
}