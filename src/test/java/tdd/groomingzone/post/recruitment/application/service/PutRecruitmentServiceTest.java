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
import tdd.groomingzone.post.recruitment.dto.PutRecruitmentCommand;
import tdd.groomingzone.post.recruitment.repository.LoadRecruitmentPort;
import tdd.groomingzone.post.recruitment.repository.SaveRecruitmentPort;
import tdd.groomingzone.post.recruitment.domain.Recruitment;
import tdd.groomingzone.post.recruitment.service.PutRecruitmentService;
import tdd.groomingzone.util.MemberCreator;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PutRecruitmentServiceTest {

    @Mock
    private LoadMemberPort loadMemberPort;

    @Mock
    private LoadRecruitmentPort loadRecruitmentPort;

    @Mock
    private SaveRecruitmentPort saveRecruitmentPort;

    @InjectMocks
    private PutRecruitmentService putRecruitmentService;

    @Test
    @DisplayName("구인구직 게시글을 수정한다.")
    void testPutRecruitment() {
        Member writer = MemberCreator.createMember();

        String testTitle = "title";
        String testContent = "content";
        int testViewCount = 1;
        long testId = 1L;
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();
        String testType = "OFFER";

        PutRecruitmentCommand putRecruitmentCommand = PutRecruitmentCommand.of(testTitle,
                testContent,
                writer.getEmail(),
                testId,
                testModifiedAt);

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

        given(loadMemberPort.findMemberByEmail(anyString())).willReturn(writer);
        given(loadRecruitmentPort.loadRecruitmentById(anyLong())).willReturn(recruitment);
        given(saveRecruitmentPort.save(any())).willReturn(recruitment);

        SingleRecruitmentResponse response = putRecruitmentService.putRecruitment(putRecruitmentCommand);

        assertThat(response.getBoardId()).isEqualTo(putRecruitmentCommand.recruitmentId());
        assertThat(response.getTitle()).isEqualTo(putRecruitmentCommand.title());
        assertThat(response.getContent()).isEqualTo(putRecruitmentCommand.content());
        assertThat(response.getModifiedAt()).isEqualTo(putRecruitmentCommand.modifiedAt());
    }
}