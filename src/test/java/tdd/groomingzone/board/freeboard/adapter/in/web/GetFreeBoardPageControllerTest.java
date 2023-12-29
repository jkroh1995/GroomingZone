package tdd.groomingzone.board.freeboard.adapter.in.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tdd.groomingzone.board.common.WriterInfo;
import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardEntityCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardPageCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.usecase.GetFreeBoardPageUseCase;
import tdd.groomingzone.global.pagedresponse.PageInfo;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = GetFreeBoardPageController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureRestDocs
class GetFreeBoardPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetFreeBoardPageUseCase getFreeBoardPageUseCase;

    @Test
    @DisplayName("게시글 페이지 조회 요청 테스트")
    void getFreeBoardPageTest() throws Exception {
        // given
        Member writer = Member.builder()
                .memberId(1L)
                .email("test@email.com")
                .password("11aA!!@@Password")
                .phoneNumber("010-1111-1111")
                .nickName("nickName")
                .role("BARBER")
                .build();

        List<FreeBoardEntityCommandResponse> testCommandResponseList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            FreeBoardEntityCommandResponse testResponse = FreeBoardEntityCommandResponse.of((i + 1),
                    "testTitle " + (i + 1),
                    "testContent " + (i + 1),
                    1,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    WriterInfo.of(writer));
            testCommandResponseList.add(testResponse);
        }

        PageInfo pageInfo = PageInfo.of(1, testCommandResponseList.size(), 20, 1);

        given(getFreeBoardPageUseCase.getFreeBoardPage(any())).willReturn(FreeBoardPageCommandResponse.of(testCommandResponseList, pageInfo));

        int fakePageNumber = 1;
        // when // then
        for (int i = 0; i < testCommandResponseList.size(); i++) {
            mockMvc.perform(
                            get("/free-board?page={page}", fakePageNumber)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(jsonPath("data", hasSize(testCommandResponseList.size())))
                    .andExpect(jsonPath("data[%d].boardId", i).value(testCommandResponseList.get(i).getBoardId()))
                    .andExpect(jsonPath("data[%d].title", i).value(testCommandResponseList.get(i).getTitle()))
                    .andExpect(jsonPath("data[%d].viewCount", i).value(testCommandResponseList.get(i).getViewCount()))
//                    .andExpect(jsonPath("data[%d].createdAt", i).value(testCommandResponseList.get(i).getCreatedAt()))   // 게시판 화면에서 작성일, 수정일이 출력될 수 있음
//                    .andExpect(jsonPath("data[%d].modifiedAt", i).value(testCommandResponseList.get(i).getModifiedAt()))
                    .andExpect(jsonPath("data[%d].writerInfo.writerId", i).value(writer.getMemberId()))
                    .andExpect(jsonPath("data[%d].writerInfo.writerNickName", i).value(writer.getNickName()));
        }
    }
}