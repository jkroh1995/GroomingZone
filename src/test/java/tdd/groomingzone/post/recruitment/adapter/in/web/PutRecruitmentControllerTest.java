package tdd.groomingzone.post.recruitment.adapter.in.web;

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
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.recruitment.application.port.in.SingleRecruitmentResponse;
import tdd.groomingzone.post.recruitment.application.port.in.usecase.PutRecruitmentUseCase;
import tdd.groomingzone.post.recruitment.domain.Recruitment;
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

@WebMvcTest(controllers = PutRecruitmentController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureRestDocs
class PutRecruitmentControllerTest {

    @MockBean
    private SecurityFilterChain oauth;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private Time stubTime;

    @MockBean
    private PutRecruitmentUseCase putRecruitmentUseCase;

    @Test
    @DisplayName("게시글 수정 요청을 보낸다.")
    void testPutRecruitment() throws Exception {
        String testTitle = "changedTitle";
        String testContent = "changedContent";
        String testType = "OFFER";
        long testId = 1L;

        RecruitmentApiDto.Put testPutDto = RecruitmentApiDto.Put.builder()
                .title(testTitle)
                .content(testContent)
                .build();

        String content = gson.toJson(testPutDto);

        Member writer = MemberCreator.createMember();

        stubTime = new StubTime(LocalDateTime.of(2023, 11, 28, 22, 30, 10));

        SingleRecruitmentResponse testResponse = SingleRecruitmentResponse.of(Recruitment.builder()
                .writer(writer)
                .id(testId)
                .title(testTitle)
                .content(testContent)
                .type(testType)
                .viewCount(1)
                .createdAt(LocalDateTime.now())
                .modifiedAt(stubTime.now())
                .build());

        given(putRecruitmentUseCase.putRecruitment(any())).willReturn(testResponse);

        mockMvc.perform(
                        put("/recruitment/{recruitment-id}", testId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(testPutDto.getTitle()))
                .andExpect(jsonPath("$.content").value(testPutDto.getContent()))
                .andDo(document("put-recruitment",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("recruitment-id").description("구인구직 글 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                )
                        ),
                        responseFields(
                                fieldWithPath("boardId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("type").type(JsonFieldType.STRING).description("구인구직 머리말"),
                                fieldWithPath("viewCount").type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("작성일"),
                                fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정일"),
                                fieldWithPath("writerInfo.writerId").type(JsonFieldType.NUMBER).description("작성한 사용자 ID"),
                                fieldWithPath("writerInfo.writerNickName").type(JsonFieldType.STRING).description("작성한 사용자 닉네임")
                        )
                ));
    }
}