package tdd.groomingzone.post.recruitment.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.recruitment.dto.SingleRecruitmentResponse;
import tdd.groomingzone.post.recruitment.dto.PostRecruitmentCommand;
import tdd.groomingzone.post.recruitment.repository.SaveRecruitmentPort;
import tdd.groomingzone.post.recruitment.domain.Recruitment;
import tdd.groomingzone.post.recruitment.service.PostRecruitmentService;
import tdd.groomingzone.util.MemberCreator;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
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
        Member writer = MemberCreator.createMember();

        String testTitle = "title";
        String testContent = "content";
        String testType = "OFFER";
        int testViewCount = 1;
        long testId = 1L;
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();

        PostRecruitmentCommand postRecruitmentCommand = PostRecruitmentCommand.of(writer.getEmail(),
                testTitle,
                testContent,
                testType);

        Recruitment recruitment = Recruitment.builder()
                .id(testId)
                .writer(writer)
                .title(testTitle)
                .content(testContent)
                .type(testType)
                .viewCount(testViewCount)
                .createdAt(testCreatedAt)
                .modifiedAt(testModifiedAt)
                .build();

        given(saveRecruitmentPort.save(any())).willReturn(recruitment);
        given(loadMemberPort.findMemberByEmail(anyString())).willReturn(writer);

        SingleRecruitmentResponse response = postRecruitmentService.postRecruitment(postRecruitmentCommand);

        assertThat(response.getTitle()).isEqualTo(postRecruitmentCommand.title());
        assertThat(response.getContent()).isEqualTo(postRecruitmentCommand.content());
        assertThat(response.getType()).isEqualTo("구인");
    }
}