package tdd.groomingzone.post.recruitment.adapter.in.web;

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
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.recruitment.dto.SingleRecruitmentResponse;
import tdd.groomingzone.post.recruitment.service.PostRecruitmentUseCase;
import tdd.groomingzone.post.recruitment.controller.PostRecruitmentController;
import tdd.groomingzone.post.recruitment.domain.Recruitment;
import tdd.groomingzone.post.recruitment.dto.RecruitmentApiDto;
import tdd.groomingzone.util.MemberCreator;

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

@WebMvcTest(controllers = PostRecruitmentController.class)
@AutoConfigureRestDocs
class PostRecruitmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private PostRecruitmentUseCase postRecruitmentUseCase;

    @MockBean
    private SecurityFilterChain oauth2SecurityFilterChain;

    @Test
    @DisplayName("구인 구직 게시글을 등록한다.")
    @WithMockUser(value = "jk@gmail.com", roles = {"BARBER"})
    void testPostRecruitment() throws Exception {
        // given
        long testId = 1L;
        String testTitle = "title";
        String testContent = "content";
        String testType = "OFFER";
        int testViewCount = 0;
        LocalDateTime testCreatedAt = LocalDateTime.now();
        LocalDateTime testModifiedAt = LocalDateTime.now();

        RecruitmentApiDto.Post testPost = RecruitmentApiDto.Post.builder()
                .title(testTitle)
                .content(testContent)
                .type(testType)
                .build();

        String content = gson.toJson(testPost);

        Member writer = MemberCreator.createMember();

        Recruitment recruitment = Recruitment.builder()
                .id(testId)
                .writer(writer)
                .title(testTitle)
                .content(testContent)
                .type(testType)
                .viewCount(testViewCount)
                .createdAt(testCreatedAt)
                .modifiedAt(testModifiedAt)
                .build();

        SingleRecruitmentResponse testResponse = SingleRecruitmentResponse.of(recruitment);

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
                .andExpect(jsonPath("$.type").value("구인"))
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