package tdd.groomingzone.board.freeboard.adapter.in.web;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import tdd.groomingzone.board.common.WriterInfo;
import tdd.groomingzone.board.freeboard.adapter.in.web.dto.FreeBoardApiDto;
import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardEntityCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.usecase.PostFreeBoardUseCase;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getResponsePreProcessor;

@WebMvcTest(controllers = PostFreeBoardController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureRestDocs
class PostFreeBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private PostFreeBoardUseCase postFreeBoardUseCase;

    @Test
    @DisplayName("정상적인 게시글 등록 요청 테스트")
    void postFreeBoardTest() throws Exception {
        // given
        long testId = 1L;
        String testTitle = "title";
        String testContent = "content";

        FreeBoardApiDto.Post testPost = FreeBoardApiDto.Post.builder()
                .title(testTitle)
                .content(testContent)
                .build();

        String content = gson.toJson(testPost);

        Member writer = Member.builder()
                .memberId(1L)
                .email("test@email.com")
                .password("11aA!!@@Password")
                .phoneNumber("010-1111-1111")
                .nickName("nickName")
                .role("BARBER")
                .build();

        FreeBoardEntityCommandResponse testResponse = FreeBoardEntityCommandResponse.of(testId,
                testTitle,
                testContent,
                1,
                LocalDateTime.now(),
                LocalDateTime.now(),
                WriterInfo.of(writer));

        given(postFreeBoardUseCase.postFreeBoard(any())).willReturn(testResponse);

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
                                        fieldWithPath("viewCount").type(JsonFieldType.NUMBER).description("조회수"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("작성일"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정일"),
                                        fieldWithPath("writerInfo.writerId").type(JsonFieldType.NUMBER).description("작성한 사용자 ID"),
                                        fieldWithPath("writerInfo.writerNickName").type(JsonFieldType.STRING).description("작성한 사용자 닉네임")
                                )
                        )
                ));
    }
}