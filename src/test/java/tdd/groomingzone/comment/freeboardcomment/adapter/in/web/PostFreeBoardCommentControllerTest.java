package tdd.groomingzone.comment.freeboardcomment.adapter.in.web;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import tdd.groomingzone.comment.freeboardcomment.controller.PostFreeBoardCommentController;
import tdd.groomingzone.comment.freeboardcomment.dto.FreeBoardCommentApiDto;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.comment.freeboardcomment.dto.SingleFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.service.PostFreeBoardCommentUseCase;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.util.MemberCreator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getResponsePreProcessor;

@WebMvcTest(PostFreeBoardCommentController.class)
@AutoConfigureRestDocs
class PostFreeBoardCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private PostFreeBoardCommentUseCase postFreeBoardCommentUseCase;

    @MockBean
    private SecurityFilterChain oauth2SecurityFilterChain;

    @Test
    @DisplayName("정상적인 게시글 등록 요청 테스트")
    @WithMockUser(value = "jk@gmail.com", roles = {"CUSTOMER"})
    void postFreeBoardTest() throws Exception {
        // given
        String testContent = "content";

        FreeBoardCommentApiDto.Post testPost = new FreeBoardCommentApiDto.Post(testContent);

        String content = gson.toJson(testPost);

        Member writer = MemberCreator.createMember();
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();

        SingleFreeBoardCommentResponse testResponse = SingleFreeBoardCommentResponse.of(testContent,
                testCreatedAt,
                testModifiedAt,
                WriterInfo.of(writer.getMemberId(), writer.getNickName()));

        given(postFreeBoardCommentUseCase.postFreeBoardComment(any())).willReturn(testResponse);

        // when // then
        mockMvc.perform(
                        post("/free-board/comment/{free-board-id}", 1)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value(testResponse.content()))
                .andExpect(jsonPath("$.createdAt").value(testResponse.createdAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))))
                .andExpect(jsonPath("$.modifiedAt").value(testResponse.modifiedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))))
                .andExpect(jsonPath("$.writerInfo.writerId").value(testResponse.writerInfo().getWriterId()))
                .andExpect(jsonPath("$.writerInfo.writerNickName").value(testResponse.writerInfo().getWriterNickName()))
                .andDo(document(
                        "post-free-board-comment",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                )
                        ),
                        pathParameters(
                                List.of(
                                        parameterWithName("free-board-id").description("자유 게시글 식별자")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("작성일"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정일"),
                                        fieldWithPath("writerInfo.writerId").type(JsonFieldType.NUMBER).description("작성한 사용자 ID"),
                                        fieldWithPath("writerInfo.writerNickName").type(JsonFieldType.STRING).description("작성한 사용자 닉네임")
                                )
                        )
                ));
    }
}