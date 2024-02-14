package tdd.groomingzone.post.freeboard.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import tdd.groomingzone.post.freeboard.adapter.out.persistence.entity.FreeBoardEntity;
import tdd.groomingzone.post.freeboard.application.port.in.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.MultiFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.command.GetFreeBoardPageCommand;
import tdd.groomingzone.post.freeboard.application.port.out.FreeBoardPage;
import tdd.groomingzone.post.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.global.pagedresponse.PageInfo;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;
import tdd.groomingzone.util.MemberCreator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        List<FreeBoard> entityQueryResults = new ArrayList<>();

        long testBoardId = 1;
        String testTitle = "title";
        String testContent = "content";
        int testViewCount = 0;
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();

        for (int i = 0; i < 5; i++) {
            entityQueryResults.add(FreeBoard.builder()
                    .writer(writer)
                    .id(testBoardId + i)
                    .title(testTitle + i)
                    .content(testContent + i)
                    .viewCount(testViewCount + i)
                    .createdAt(testCreatedAt)
                    .modifiedAt(testModifiedAt)
                    .build());
        }

        List<FreeBoardEntity> freeBoardEntityList = entityQueryResults.stream()
                .map(entity -> FreeBoardEntity.builder()
                        .boardId(entity.getId())
                        .writerId(entity.getWriterId())
                        .title(entity.getTitle())
                        .content(entity.getContent())
                        .viewCount(entity.getViewCount())
                        .createdAt(entity.getCreatedAt())
                        .modifiedAt(entity.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
        Page<FreeBoardEntity> freeBoardEntityPage = new PageImpl<>(freeBoardEntityList);
        FreeBoardPage freeBoardPageQueryResult = FreeBoardPage.of(entityQueryResults, freeBoardEntityPage);
        given(loadFreeBoardPort.loadFreeBoardPage(anyString(), anyString(), anyString(), any())).willReturn(freeBoardPageQueryResult);

        GetFreeBoardPageCommand command = GetFreeBoardPageCommand.of(testTitle,
                testContent,
                "nickName",
                testPageIndex + 1);
        MultiFreeBoardCommandResponse pageCommandResponse = getFreeBoardPageService.getFreeBoardPage(command);

        List<SingleFreeBoardCommandResponse> pageResponse = pageCommandResponse.getPageResponse();
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

        assertThat(pageInfo.getPageNumber()).isEqualTo(freeBoardEntityPage.getNumber() + 1);
        assertThat(pageInfo.getTotalPage()).isEqualTo(freeBoardEntityPage.getTotalPages());
        assertThat(pageInfo.getSize()).isEqualTo(freeBoardEntityPage.getSize());
        assertThat(pageInfo.getTotalElements()).isEqualTo(freeBoardEntityPage.getTotalElements());
    }
}