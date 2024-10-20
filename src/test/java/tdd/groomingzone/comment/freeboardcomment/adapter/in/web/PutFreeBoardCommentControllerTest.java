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
import tdd.groomingzone.comment.freeboardcomment.controller.PutFreeBoardCommentController;
import tdd.groomingzone.comment.freeboardcomment.dto.FreeBoardCommentApiDto;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.comment.freeboardcomment.dto.SingleFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.service.PutFreeBoardCommentUseCase;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.util.MemberCreator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getResponsePreProcessor;

@WebMvcTest(PutFreeBoardCommentController.class)
@AutoConfigureRestDocs
public class PutFreeBoardCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private PutFreeBoardCommentUseCase putFreeBoardCommentUseCase;

    @MockBean
    private SecurityFilterChain oauth2SecurityFilterChain;

    @Test
    @DisplayName("댓글을 수정한다.")
    @WithMockUser(value = "jk@gmail.com", roles = {"CUSTOMER"})
    void testPutFreeBoardComment() throws Exception {

        String testContent = "content";
        FreeBoardCommentApiDto.Put dto = new FreeBoardCommentApiDto.Put(testContent);

        String content = gson.toJson(dto);

        Member writer = MemberCreator.createMember();
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();
        SingleFreeBoardCommentResponse response = SingleFreeBoardCommentResponse.of(testContent,
                testCreatedAt,
                testModifiedAt,
                WriterInfo.of(writer.getMemberId(), writer.getNickName()));

        given(putFreeBoardCommentUseCase.putFreeBoard(any())).willReturn(response);

        mockMvc.perform(
                        put("/free-board/comment/{free-board-id}/{comment-id}", 1L, 1L)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(response.content()))
                .andExpect(jsonPath("$.createdAt").value(response.createdAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))))
                .andExpect(jsonPath("$.modifiedAt").value(response.modifiedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))))
                .andExpect(jsonPath("$.writerInfo.writerId").value(response.writerInfo().getWriterId()))
                .andExpect(jsonPath("$.writerInfo.writerNickName").value(response.writerInfo().getWriterNickName()))
                .andDo(document(
                        "put-free-board-comment",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                )
                        ),
                        pathParameters(
                                List.of(
                                        parameterWithName("free-board-id").description("자유 게시글 식별자"),
                                        parameterWithName("comment-id").description("자유 게시글 댓글 식별자")
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
