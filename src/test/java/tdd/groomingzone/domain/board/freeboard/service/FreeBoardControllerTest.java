package tdd.groomingzone.domain.board.freeboard.service;

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
import tdd.groomingzone.domain.board.freeboard.dto.FreeBoardDto;
import tdd.groomingzone.domain.board.freeboard.controller.FreeBoardController;
import tdd.groomingzone.util.StubTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
    private FreeBoardServiceManager freeBoardService;

    @MockBean
    private StubTime stubTime;

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
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .modifiedAt(LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
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
                                        fieldWithPath("boardId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("작성일"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정일")
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
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .modifiedAt(LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .build();

        String content = gson.toJson(testPut);

        given(freeBoardService.putFreeBoard(anyLong(), any(), any())).willReturn(testResponse);

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
                                        fieldWithPath("boardId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("작성일"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정일")
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
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .modifiedAt(LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
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
                                        fieldWithPath("boardId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("작성일"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정일")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("게시글 페이지 조회 요청 테스트")
    void getFreeBoardPageTest() throws Exception {
        // given
        int fakePage = 1;

        List<FreeBoardDto.Response> fakeResponseList = List.of(
                FreeBoardDto.Response.builder().boardId(1).title("1").content("1").createdAt("1").modifiedAt("1").build(),
                FreeBoardDto.Response.builder().boardId(2).title("2").content("2").createdAt("2").modifiedAt("2").build(),
                FreeBoardDto.Response.builder().boardId(3).title("3").content("3").createdAt("3").modifiedAt("3").build(),
                FreeBoardDto.Response.builder().boardId(4).title("4").content("4").createdAt("4").modifiedAt("4").build(),
                FreeBoardDto.Response.builder().boardId(5).title("5").content("5").createdAt("5").modifiedAt("5").build(),
                FreeBoardDto.Response.builder().boardId(5).title("6").content("6").createdAt("6").modifiedAt("6").build()
        );

        given(freeBoardService.getFreeBoardPage(anyInt())).willReturn(fakeResponseList);

        // when // then
        for(int i = 0;i<fakeResponseList.size();i++){
            mockMvc.perform(
                            get("/free-board?page={page}", fakePage)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(jsonPath("$", hasSize(fakeResponseList.size())))
                    .andExpect(jsonPath("$[%d].boardId",i).value(fakeResponseList.get(i).getBoardId()))
                    .andExpect(jsonPath("$[%d].title",i).value(fakeResponseList.get(i).getTitle()))
                    .andExpect(jsonPath("$[%d].content",i).value(fakeResponseList.get(i).getContent()))
                    .andExpect(jsonPath("$[%d].createdAt",i).value(fakeResponseList.get(i).getCreatedAt()))
                    .andExpect(jsonPath("$[%d].modifiedAt",i).value(fakeResponseList.get(i).getModifiedAt()));
        }

        mockMvc.perform(
                        get("/free-board?page={page}", fakePage)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(document(
                        "get-paged-free-board",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                parameterWithName("page").description("페이지 번호")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("[].boardId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                        fieldWithPath("[].title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("[].content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("작성일"),
                                        fieldWithPath("[].modifiedAt").type(JsonFieldType.STRING).description("수정일")
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