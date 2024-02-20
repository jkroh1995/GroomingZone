package tdd.groomingzone.post.freeboard.adapter.in.web;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import tdd.groomingzone.post.freeboard.application.port.in.usecase.DeleteFreeBoardUseCase;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getResponsePreProcessor;

@WebMvcTest(DeleteFreeBoardController.class)
@AutoConfigureRestDocs
class DeleteFreeBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeleteFreeBoardUseCase deleteFreeBoardUseCase;

    @MockBean
    private SecurityFilterChain oauth2SecurityFilterChain;

    @Test
    @DisplayName("정상적인 게시글 삭제 요청 테스트")
    @WithMockUser(value = "jk@gmail.com", roles = {"CUSTOMER"})
    void deleteFreeBoardTest() throws Exception {
        // given
        long testId = 1L;

        // when // then
        mockMvc.perform(
                        delete("/free-board/{free-board-id}", testId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNoContent())
                .andDo(document(
                        "delete-free-board",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("free-board-id").description("자유 게시글 식별자")
                        )
                ));
    }
}