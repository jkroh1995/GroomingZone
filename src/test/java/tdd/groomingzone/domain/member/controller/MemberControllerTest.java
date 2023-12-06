package tdd.groomingzone.domain.member.controller;

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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import tdd.groomingzone.domain.member.dto.MemberDto;
import tdd.groomingzone.domain.member.service.MemberServiceManager;
import tdd.groomingzone.util.StubTime;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tdd.groomingzone.global.util.ApiDocumentUtils.getRequestPreProcessor;
import static tdd.groomingzone.global.util.ApiDocumentUtils.getResponsePreProcessor;

@WebMvcTest(controllers =  MemberController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureRestDocs
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private MemberServiceManager memberService;

    @MockBean
    private StubTime stubTime;

    @Test
    @DisplayName("정상적인 회원 가입 요청 테스트")
    @WithAnonymousUser
    void postMemberTest() throws Exception {
        // given
        String email = "email";
        String password = "password";
        String name = "name";
        String phoneNumber = "phoneNumber";
        String role = "바버";

        MemberDto.Post postDto = createPostDto(email, password, name, phoneNumber, role);

        MemberDto.Response testResponse = MemberDto.Response.builder()
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .role("BARBER")
                .build();

        String content = gson.toJson(postDto);

        given(memberService.postMember(any())).willReturn(testResponse);

        // when // then
        mockMvc.perform(
                        post("/member")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(postDto.email))
                .andExpect(jsonPath("$.name").value(postDto.name))
                .andExpect(jsonPath("$.phoneNumber").value(postDto.phoneNumber))
                .andExpect(jsonPath("$.role").value("BARBER"))
                .andDo(document(
                        "post-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀 번호"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("전화 번호"),
                                        fieldWithPath("role").type(JsonFieldType.STRING).description("역할")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("전화 번호"),
                                        fieldWithPath("role").type(JsonFieldType.STRING).description("역할")
                                )
                        )
                ));
    }

    private MemberDto.Post createPostDto(String email, String password, String name, String phoneNumber, String role) {
        MemberDto.Post postDto = new MemberDto.Post();
        postDto.email = email;
        postDto.password = password;
        postDto.name = name;
        postDto.phoneNumber = phoneNumber;
        postDto.role = role;
        return postDto;
    }
}