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
import org.springframework.test.web.servlet.MockMvc;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.post.recruitment.application.port.in.SingleRecruitmentResponse;
import tdd.groomingzone.post.recruitment.application.port.in.usecase.PostRecruitmentUseCase;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getResponsePreProcessor;

@WebMvcTest(controllers = PostRecruitmentController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureRestDocs
class PostRecruitmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private PostRecruitmentUseCase postRecruitmentUseCase;

    @Test
    @DisplayName("구인 구직 게시글을 등록한다.")
    void testPostRecruitment() throws Exception {
        // given
        long testId = 1L;
        String testTitle = "title";
        String testContent = "content";
        String testType = "OFFER";

        RecruitmentApiDto.Post testPost = RecruitmentApiDto.Post.builder()
                .title(testTitle)
                .content(testContent)
                .type(testType)
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

        SingleRecruitmentResponse testResponse = SingleRecruitmentResponse.of(testId,
                testTitle,
                testContent,
                testType,
                1,
                LocalDateTime.now(),
                LocalDateTime.now(),
                WriterInfo.of(writer));

        given(postRecruitmentUseCase.postRecruitment(any())).willReturn(testResponse);

        // when // then
        mockMvc.perform(
                        post("/recruitment")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(testPost.getTitle()))
                .andExpect(jsonPath("$.content").value(testPost.getContent()))
                .andExpect(jsonPath("$.type").value(testPost.getType()))
                .andDo(document(
                        "post-recruitment",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("type").type(JsonFieldType.STRING).description("글머리")
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
                                        fieldWithPath("type").type(JsonFieldType.STRING).description("글머리"),
                                        fieldWithPath("writerInfo.writerId").type(JsonFieldType.NUMBER).description("작성한 사용자 ID"),
                                        fieldWithPath("writerInfo.writerNickName").type(JsonFieldType.STRING).description("작성한 사용자 닉네임")
                                )
                        )
                ));
    }
}