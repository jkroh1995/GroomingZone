package tdd.groomingzone.comment.freeboardcomment.adapter.in.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.MultiFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.SingleFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase.GetFreeBoardCommentUseCase;
import tdd.groomingzone.global.pagedresponse.PageInfo;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.util.MemberCreator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getResponsePreProcessor;

@WebMvcTest(GetFreeBoardCommentController.class)
@AutoConfigureRestDocs
class GetFreeBoardCommentControllerTest {

    @MockBean
    private SecurityFilterChain oauth2SecurityFilterChain;

    @MockBean
    private GetFreeBoardCommentUseCase getFreeBoardCommentUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("댓글 목록을 조회한다.")
    void testGetFreeBoardComment() throws Exception {
        String testContent = "content";

        Member writer = MemberCreator.createMember();

        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();

        List<SingleFreeBoardCommentResponse> responseList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            responseList.add(SingleFreeBoardCommentResponse.of(testContent + i,
                    testCreatedAt,
                    testModifiedAt,
                    WriterInfo.of(writer.getMemberId(), writer.getNickName())));
        }

        PageInfo pageInfo = PageInfo.of(1, responseList.size(), 20, 1);

        MultiFreeBoardCommentResponse commentResponse = MultiFreeBoardCommentResponse.of(responseList, pageInfo);
        given(getFreeBoardCommentUseCase.getFreeBoardComment(any())).willReturn(commentResponse);

        // when // then
        for (int i = 0; i < responseList.size(); i++) {
            mockMvc.perform(
                            get("/free-board/comment/{free-board-id}?page={page}", 1, 1)
                                    .param("page", "1")
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andExpect(jsonPath("data", hasSize(responseList.size())))
                    .andExpect(jsonPath("data[%d].content", i).value(responseList.get(i).getContent()))
                    .andExpect(jsonPath("data[%d].createdAt", i).value(responseList.get(i).getCreatedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))))
                    .andExpect(jsonPath("data[%d].modifiedAt", i).value(responseList.get(i).getModifiedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))))
                    .andExpect(jsonPath("data[%d].writerInfo.writerId", i).value(writer.getMemberId()))
                    .andExpect(jsonPath("data[%d].writerInfo.writerNickName", i).value(writer.getNickName()))
                    .andExpect(jsonPath("pageInfo.pageNumber").value(pageInfo.getPageNumber()))
                    .andExpect(jsonPath("pageInfo.size").value(pageInfo.getSize()))
                    .andExpect(jsonPath("pageInfo.totalElements").value(pageInfo.getTotalElements()))
                    .andExpect(jsonPath("pageInfo.totalPage").value(pageInfo.getTotalPage()))
                    .andDo(document("get-free-board-comment",
                            getRequestPreProcessor(),
                            getResponsePreProcessor(),
                            pathParameters(
                                    parameterWithName("free-board-id").description("자유 게시글 식별자")
                            ),
                            queryParameters(
                                    parameterWithName("page").description("댓글 페이지 번호")
                            ),
                            responseFields(
                                    List.of(
                                            fieldWithPath("data[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                                            fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("댓글 작성일"),
                                            fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("댓글 최종 수정일"),
                                            fieldWithPath("data[].writerInfo.writerId").type(JsonFieldType.NUMBER).description("작성한 사용자 ID"),
                                            fieldWithPath("data[].writerInfo.writerNickName").type(JsonFieldType.STRING).description("작성한 사용자 닉네임"),
                                            fieldWithPath("pageInfo.pageNumber").type(JsonFieldType.NUMBER).description("검색한 페이지 번호"),
                                            fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("검색한 페이지의 댓글 수"),
                                            fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 댓글 수"),
                                            fieldWithPath("pageInfo.totalPage").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                                    )
                            )
                    ));
        }
    }
}