package tdd.groomingzone.post.freeboard.adapter.in.web;

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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import tdd.groomingzone.global.time.Time;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.post.freeboard.adapter.in.web.dto.FreeBoardApiDto;
import tdd.groomingzone.post.freeboard.application.port.in.FreeBoardEntityCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.usecase.PutFreeBoardUseCase;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.util.MemberCreator;
import tdd.groomingzone.util.StubTime;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getResponsePreProcessor;


@WebMvcTest(controllers = {PutFreeBoardController.class},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureRestDocs
class PutFreeBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private SecurityFilterChain oauth2SecurityFilterChain;

    @MockBean
    Time stubTime;

    @MockBean
    private PutFreeBoardUseCase putFreeBoardUseCase;

    @Test
    @DisplayName("정상적인 게시글 수정 요청 테스트")
    void putFreeBoardTest() throws Exception {
        // given
        String testTitle = "changedTitle";
        String testContent = "changedContent";
        long testId = 1L;

        FreeBoardApiDto.Put testPut = FreeBoardApiDto.Put.builder()
                .title(testTitle)
                .content(testContent)
                .build();

        String content = gson.toJson(testPut);

        Member writer = MemberCreator.createMember();

        stubTime = new StubTime(LocalDateTime.of(2023, 11, 28, 22, 30, 10));

        FreeBoardEntityCommandResponse testResponse = FreeBoardEntityCommandResponse.of(testId,
                testTitle,
                testContent,
                1,
                LocalDateTime.now(),
                stubTime.now(),
                WriterInfo.of(writer));

        given(putFreeBoardUseCase.putFreeBoard(any())).willReturn(testResponse);

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