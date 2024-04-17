package tdd.groomingzone.post.freeboard.domain;

import org.junit.jupiter.api.Test;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.common.BoardInfo;
import tdd.groomingzone.util.MemberCreator;
import tdd.groomingzone.util.StubTime;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FreeBoardTest {

    @Test
    void testModify() {
        Member writer = MemberCreator.createMember();

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

        testEntity.modify(modifiedTitle, modifiedContent, fakeModifiedTime);
        assertThat(testEntity.getTitle()).isEqualTo(modifiedTitle);
        assertThat(testEntity.getContent()).isEqualTo(modifiedContent);
        assertThat(testEntity.getModifiedAt()).isEqualTo(fakeModifiedTime);
    }

    @Test
    void testViewed() {
        Member writer = MemberCreator.createMember();

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
        Member writer = MemberCreator.createMember();

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
                .provider("SERVER")
                .build();

        assertThrows(BusinessException.class,() -> testEntity.checkMemberAuthority(otherMember));
    }
}