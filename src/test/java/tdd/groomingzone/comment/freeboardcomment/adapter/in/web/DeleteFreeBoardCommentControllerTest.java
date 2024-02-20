package tdd.groomingzone.comment.freeboardcomment.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase.DeleteFreeBoardCommentUseCase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getResponsePreProcessor;

@WebMvcTest(DeleteFreeBoardCommentController.class)
@AutoConfigureRestDocs
class DeleteFreeBoardCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityFilterChain oauth2SecurityFilterChain;

    @MockBean
    private DeleteFreeBoardCommentUseCase deleteFreeBoardCommentUseCase;

    @Test
    @WithMockUser(value = "jk@gmail.com", roles = {"CUSTOMER"})
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

        verify(deleteFreeBoardCommentUseCase).delete(any());
    }
}