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
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();

        PostRecruitmentCommand postRecruitmentCommand = PostRecruitmentCommand.of(writer.getEmail(),
                testTitle,
                testContent,
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
        given(loadMemberPort.findMemberByEmail(anyString())).willReturn(writer);

        SingleRecruitmentResponse response = postRecruitmentService.postRecruitment(postRecruitmentCommand);

        assertThat(response.getTitle()).isEqualTo(postRecruitmentCommand.getTitle());
        assertThat(response.getContent()).isEqualTo(postRecruitmentCommand.getContent());
        assertThat(response.getType()).isEqualTo(postRecruitmentCommand.getType());
    }
}