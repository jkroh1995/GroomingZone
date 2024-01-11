package tdd.groomingzone.post.recruitment.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.recruitment.application.port.in.SingleRecruitmentResponse;
import tdd.groomingzone.post.recruitment.application.port.in.command.PostRecruitmentCommand;
import tdd.groomingzone.post.recruitment.application.port.out.RecruitmentEntityQueryResult;
import tdd.groomingzone.post.recruitment.application.port.out.SaveRecruitmentPort;
import tdd.groomingzone.post.recruitment.application.port.out.SaveRecruitmentQuery;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostRecruitmentServiceTest {

    @Mock
    private LoadMemberPort loadMemberPort;

    @Mock
    private SaveRecruitmentPort saveRecruitmentPort;

    @InjectMocks
    private PostRecruitmentService postRecruitmentService;

    @Test
    @DisplayName("구인구직 게시글을 저장한다")
    void testPostRecruitment() {
        //given
        Member writer = Member.builder()
                .memberId(1L)
                .email("test@email.com")
                .password("11aA!!@@Password")
                .phoneNumber("010-1111-1111")
                .nickName("nickName")
                .role("BARBER")
                .build();

        String testTitle = "title";
        String testContent = "content";
        String testType = "OFFER";
        int testViewCount = 1;
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();

        PostRecruitmentCommand postRecruitmentCommand = PostRecruitmentCommand.of(writer.getMemberId(),
                testTitle,
                testContent,
                testType);

        SaveRecruitmentQuery saveRecruitmentQuery = SaveRecruitmentQuery.of(writer.getMemberId(),
                writer.getNickName(),
                1L,
                testTitle,
                testContent,
                testViewCount,
                testCreatedAt,
                testModifiedAt,
                testType);

        RecruitmentEntityQueryResult entityQueryResult = RecruitmentEntityQueryResult.of(1L,
                writer.getMemberId(),
                writer.getNickName(),
                testTitle,
                testContent,
                testType,
                testViewCount,
                testCreatedAt,
                testModifiedAt);

        given(saveRecruitmentPort.save(any())).willReturn(entityQueryResult);
        given(loadMemberPort.findMemberById(anyLong())).willReturn(writer);

        SingleRecruitmentResponse response = postRecruitmentService.postRecruitment(postRecruitmentCommand);

        assertThat(response.getBoardId()).isEqualTo(saveRecruitmentQuery.getBoardId());
        assertThat(response.getTitle()).isEqualTo(saveRecruitmentQuery.getTitle());
        assertThat(response.getContent()).isEqualTo(saveRecruitmentQuery.getContent());
        assertThat(response.getViewCount()).isEqualTo(saveRecruitmentQuery.getViewCount());
        assertThat(response.getCreatedAt()).isEqualTo(saveRecruitmentQuery.getCreatedAt());
        assertThat(response.getModifiedAt()).isEqualTo(saveRecruitmentQuery.getModifiedAt());
        assertThat(response.getType()).isEqualTo(saveRecruitmentQuery.getType());
        assertThat(response.getWriterInfo().getWriterId()).isEqualTo(writer.getMemberId());
        assertThat(response.getWriterInfo().getWriterNickName()).isEqualTo(writer.getNickName());
    }
}