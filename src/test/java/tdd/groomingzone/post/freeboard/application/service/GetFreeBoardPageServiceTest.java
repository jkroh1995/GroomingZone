package tdd.groomingzone.post.freeboard.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.post.freeboard.application.port.in.FreeBoardEntityCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.FreeBoardPageCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.command.GetFreeBoardPageCommand;
import tdd.groomingzone.post.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.post.freeboard.application.port.out.FreeBoardPageQueryResult;
import tdd.groomingzone.post.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.global.pagedresponse.PageInfo;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.util.MemberCreator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetFreeBoardPageServiceTest {

    @Mock
    private LoadFreeBoardPort loadFreeBoardPort;

    @Mock
    private LoadMemberPort loadMemberPort;

    @InjectMocks
    private GetFreeBoardPageService getFreeBoardPageService;

    @Test
    @DisplayName("자유 게시글 페이지를 읽어온다.")
    void testGetFreeBoardPage() {
        Member writer = MemberCreator.createMember();
        given(loadMemberPort.findMemberById(anyLong())).willReturn(writer);

        int testPageIndex = 0;
        int testPageSize = 20;
        long testTotalElements = 20;
        int testTotalPages = 1;

        List<FreeBoardEntityQueryResult> entityQueryResults = new ArrayList<>();

        long testBoardId = 1;
        String testTitle = "title";
        String testContent = "content";
        int testViewCount = 0;
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();

        for (int i = 0; i < 5; i++) {
            entityQueryResults.add(FreeBoardEntityQueryResult.of(testBoardId + i,
                    testTitle + i,
                    testContent + i,
                    testViewCount + i,
                    testCreatedAt,
                    testModifiedAt,
                    writer.getMemberId()));
        }

        FreeBoardPageQueryResult freeBoardPageQueryResult = FreeBoardPageQueryResult.of(entityQueryResults,
                testPageIndex,
                testPageSize,
                testTotalElements,
                testTotalPages);
        given(loadFreeBoardPort.loadFreeBoardPage(anyString(), anyString(), anyString(), any())).willReturn(freeBoardPageQueryResult);

        GetFreeBoardPageCommand command = GetFreeBoardPageCommand.of(testTitle,
                testContent,
                "nickName",
                testPageIndex + 1);
        FreeBoardPageCommandResponse pageCommandResponse = getFreeBoardPageService.getFreeBoardPage(command);

        List<FreeBoardEntityCommandResponse> pageResponse = pageCommandResponse.getPageResponse();
        PageInfo pageInfo = pageCommandResponse.getPageInfo();

        for (int i = 0; i < pageResponse.size(); i++) {
            assertThat(pageResponse.get(i).getBoardId()).isEqualTo(entityQueryResults.get(i).getId());
            assertThat(pageResponse.get(i).getTitle()).isEqualTo(entityQueryResults.get(i).getTitle());
            assertThat(pageResponse.get(i).getContent()).isEqualTo(entityQueryResults.get(i).getContent());
            assertThat(pageResponse.get(i).getViewCount()).isEqualTo(entityQueryResults.get(i).getViewCount());
            assertThat(pageResponse.get(i).getCreatedAt()).isEqualTo(entityQueryResults.get(i).getCreatedAt());
            assertThat(pageResponse.get(i).getModifiedAt()).isEqualTo(entityQueryResults.get(i).getModifiedAt());
            assertThat(pageResponse.get(i).getWriterInfo().getWriterId()).isEqualTo(writer.getMemberId());
            assertThat(pageResponse.get(i).getWriterInfo().getWriterNickName()).isEqualTo(writer.getNickName());
        }

        assertThat(pageInfo.getPageNumber()).isEqualTo(testPageIndex + 1);
        assertThat(pageInfo.getTotalPage()).isEqualTo(testTotalPages);
        assertThat(pageInfo.getSize()).isEqualTo(testPageSize);
        assertThat(pageInfo.getTotalElements()).isEqualTo(testTotalElements);
    }
}