package tdd.groomingzone.post.freeboard.adapter.in.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.post.freeboard.dto.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.dto.MultiFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.service.GetFreeBoardPageUseCase;
import tdd.groomingzone.global.pagedresponse.PageInfo;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.freeboard.controller.GetFreeBoardPageController;
import tdd.groomingzone.util.MemberCreator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getResponsePreProcessor;

@WebMvcTest(controllers = GetFreeBoardPageController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureRestDocs
class GetFreeBoardPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetFreeBoardPageUseCase getFreeBoardPageUseCase;

    @MockBean
    private SecurityFilterChain oauth2SecurityFilterChain;

    @Test
    @DisplayName("게시글 페이지 조회 요청 테스트")
    void getFreeBoardPageTest() throws Exception {
        // given
        Member writer = MemberCreator.createMember();

        List<SingleFreeBoardCommandResponse> testCommandResponseList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            SingleFreeBoardCommandResponse testResponse = SingleFreeBoardCommandResponse.of((i + 1),
                    "testTitle " + (i + 1),
                    "testContent " + (i + 1),
                    1,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    WriterInfo.of(writer.getMemberId(), writer.getNickName()));
            testCommandResponseList.add(testResponse);
        }

        PageInfo pageInfo = PageInfo.of(1, testCommandResponseList.size(), 20, 1);

        given(getFreeBoardPageUseCase.getFreeBoardPage(any())).willReturn(MultiFreeBoardCommandResponse.of(testCommandResponseList, pageInfo));

        int fakePageNumber = 1;
        // when // then
        for (int i = 0; i < testCommandResponseList.size(); i++) {
            mockMvc.perform(
                            get("/free-board?page={page}&title={title}&content={content}&writer={writer}",
                                    fakePageNumber,
                                    "title",
                                    "content",
                                    "writer")
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(jsonPath("data", hasSize(testCommandResponseList.size())))
                    .andExpect(jsonPath("data[%d].boardId", i).value(testCommandResponseList.get(i).boardId()))
                    .andExpect(jsonPath("data[%d].title", i).value(testCommandResponseList.get(i).title()))
                    .andExpect(jsonPath("data[%d].viewCount", i).value(testCommandResponseList.get(i).viewCount()))
                    .andExpect(jsonPath("data[%d].writerInfo.writerId", i).value(writer.getMemberId()))
                    .andExpect(jsonPath("data[%d].writerInfo.writerNickName", i).value(writer.getNickName()))
                    .andDo(document("get-free-board-page",
                            getRequestPreProcessor(),
                            getResponsePreProcessor(),
                            queryParameters(
                                    parameterWithName("page").description("자유 게시글 페이지 번호"),
                                    parameterWithName("title").description("검색할 게시글 제목"),
                                    parameterWithName("content").description("검색할 게시글 내용"),
                                    parameterWithName("writer").description("작성자 닉네임")
                            ),
                            responseFields(
                                    List.of(
                                            fieldWithPath("data[].boardId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                            fieldWithPath("data[].title").type(JsonFieldType.STRING).description("제목"),
                                            fieldWithPath("data[].viewCount").type(JsonFieldType.NUMBER).description("조회수"),
//                                            fieldWithPath("createdAt").type(JsonFieldType.STRING).description("작성일"),
//                                            fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정일"),
                                            fieldWithPath("data[].writerInfo.writerId").type(JsonFieldType.NUMBER).description("작성한 사용자 ID"),
                                            fieldWithPath("data[].writerInfo.writerNickName").type(JsonFieldType.STRING).description("작성한 사용자 닉네임"),
                                            fieldWithPath("pageInfo.pageNumber").type(JsonFieldType.NUMBER).description("검색한 페이지 번호"),
                                            fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("검색한 페이지의 자유 게시글 수"),
                                            fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 자유 게시글 수"),
                                            fieldWithPath("pageInfo.totalPage").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                                    )
                            )
                    ));
        }
    }
}