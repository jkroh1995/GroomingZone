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
import tdd.groomingzone.post.recruitment.application.port.in.command.PutRecruitmentCommand;
import tdd.groomingzone.post.recruitment.application.port.out.LoadRecruitmentPort;
import tdd.groomingzone.post.recruitment.application.port.out.RecruitmentEntityQueryResult;
import tdd.groomingzone.post.recruitment.application.port.out.SaveRecruitmentPort;
import tdd.groomingzone.post.recruitment.application.port.out.SaveRecruitmentQuery;
import tdd.groomingzone.post.recruitment.domain.Recruitment;
import tdd.groomingzone.util.MemberCreator;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();
        String testType = "OFFER";

        PutRecruitmentCommand putRecruitmentCommand = PutRecruitmentCommand.of(testTitle,
                testContent,
                writer.getMemberId(),
                1L,
                testModifiedAt);

        SaveRecruitmentQuery saveRecruitmentQuery = SaveRecruitmentQuery.of(Recruitment.builder()
                .writer(writer)
                .id(1L)
                .title(testTitle)
                .content(testContent)
                .createdAt(testCreatedAt)
                .modifiedAt(testModifiedAt)
                .viewCount(testViewCount)
                .type(testType)
                .build());

        RecruitmentEntityQueryResult entityQueryResult = RecruitmentEntityQueryResult.of(1L, writer.getMemberId(), writer.getNickName(), testTitle,testContent, testType, testViewCount, testCreatedAt, testModifiedAt);


        given(loadMemberPort.findMemberById(anyLong())).willReturn(writer);
        given(loadRecruitmentPort.loadRecruitmentById(anyLong())).willReturn(entityQueryResult);
        given(saveRecruitmentPort.save(any())).willReturn(entityQueryResult);

        SingleRecruitmentResponse response = putRecruitmentService.putRecruitment(putRecruitmentCommand);

        assertThat(response.getBoardId()).isEqualTo(saveRecruitmentQuery.getBoardId());
        assertThat(response.getTitle()).isEqualTo(saveRecruitmentQuery.getTitle());
        assertThat(response.getContent()).isEqualTo(saveRecruitmentQuery.getContent());
        assertThat(response.getViewCount()).isEqualTo(saveRecruitmentQuery.getViewCount());
        assertThat(response.getCreatedAt()).isEqualTo(saveRecruitmentQuery.getCreatedAt());
        assertThat(response.getModifiedAt()).isEqualTo(saveRecruitmentQuery.getModifiedAt());
        assertThat(response.getType()).isEqualTo("구인");
        assertThat(response.getWriterInfo().getWriterId()).isEqualTo(writer.getMemberId());
        assertThat(response.getWriterInfo().getWriterNickName()).isEqualTo(writer.getNickName());

    }
}