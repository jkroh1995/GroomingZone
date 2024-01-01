package tdd.groomingzone.comment.freeboardcomment.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getResponsePreProcessor;

@WebMvcTest(controllers = DeleteFreeBoardCommentController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureRestDocs
class DeleteFreeBoardCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deleteFreeBoardComment() throws Exception {
        // given
        long testId = 1L;

        // when // then
        mockMvc.perform(
                        delete("/free-board/comment/{free-board-id}/{comment-id}", testId, 1L)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNoContent())
                .andDo(document(
                        "delete-free-board-comment",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("free-board-id").description("자유 게시글 식별자"),
                                parameterWithName("comment-id").description("자유 게시글 댓글 식별자")
                        )
                ));
    }
}