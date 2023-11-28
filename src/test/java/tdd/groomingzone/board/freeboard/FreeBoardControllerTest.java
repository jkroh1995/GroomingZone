package tdd.groomingzone.board.freeboard;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import tdd.groomingzone.domain.board.freeboard.FreeBoardDto;
import tdd.groomingzone.domain.board.freeboard.controller.FreeBoardController;
import tdd.groomingzone.domain.board.freeboard.service.FreeBoardServiceImpl;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tdd.groomingzone.global.util.ApiDocumentUtils.getRequestPreProcessor;
import static tdd.groomingzone.global.util.ApiDocumentUtils.getResponsePreProcessor;

@WebMvcTest(FreeBoardController.class)
@AutoConfigureRestDocs
class FreeBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private FreeBoardServiceImpl freeBoardService;

    @Test
    @DisplayName("정상적인 게시글 등록 요청 테스트")
    void postFreeBoardTest() throws Exception {
        // given
        long testId = 1L;
        String testTitle = "title";
        String testContent = "content";

        FreeBoardDto.Post testPost = new FreeBoardDto.Post();
        testPost.setTitle(testTitle);
        testPost.setContent(testContent);

        FreeBoardDto.Response testResponse = FreeBoardDto.Response
                .builder()
                .boardId(testId)
                .title(testTitle)
                .content(testContent)
                .build();

        String content = gson.toJson(testPost);

        given(freeBoardService.postFreeBoard(any())).willReturn(testResponse);

        // when // then
        mockMvc.perform(
                        post("/free-board")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(testPost.getTitle()))
                .andExpect(jsonPath("$.content").value(testPost.getContent()))
                .andDo(document(
                        "post-free-board",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("boardId").type(JsonFieldType.NUMBER).description("게시글 식별자"),           // (5)
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("정상적인 게시글 수정 요청 테스트")
    void putFreeBoardTest() throws Exception {
        // given
        String testTitle = "changedTitle";
        String testContent = "changedContent";
        long testId = 1L;

        FreeBoardDto.Put testPut = new FreeBoardDto.Put();
        testPut.setTitle(testTitle);
        testPut.setContent(testContent);

        FreeBoardDto.Response testResponse = FreeBoardDto.Response
                .builder()
                .boardId(testId)
                .title(testTitle)
                .content(testContent)
                .build();

        String content = gson.toJson(testPut);

        given(freeBoardService.putFreeBoard(anyLong(), any())).willReturn(testResponse);

        // when // then
        mockMvc.perform(
                        put("/free-board/{free-board-id}", testId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(testPut.getTitle()))
                .andExpect(jsonPath("$.content").value(testPut.getContent()))
                .andDo(document("put-free-board",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("free-board-id").description("자유 게시글 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("boardId").type(JsonFieldType.NUMBER).description("게시글 식별자"),           // (5)
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("정상적인 게시글 조회 요청 테스트")
    void getFreeBoardTest() throws Exception {
        // given
        String testTitle = "changedTitle";
        String testContent = "changedContent";
        long testId = 1L;

        FreeBoardDto.Response testResponse = FreeBoardDto.Response
                .builder()
                .title(testTitle)
                .content(testContent)
                .build();

        given(freeBoardService.getFreeBoard(anyLong())).willReturn(testResponse);

        // when // then
        mockMvc.perform(
                        get("/free-board/{free-board-id}", testId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(testTitle))
                .andExpect(jsonPath("$.content").value(testContent))
                .andDo(document(
                        "get-free-board",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("free-board-id").description("자유 게시글 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("boardId").type(JsonFieldType.NUMBER).description("게시글 식별자"),           // (5)
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("정상적인 게시글 삭제 요청 테스트")
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