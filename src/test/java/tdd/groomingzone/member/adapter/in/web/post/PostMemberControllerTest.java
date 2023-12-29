package tdd.groomingzone.member.adapter.in.web.post;

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
import tdd.groomingzone.member.adapter.dto.MemberApiDto;
import tdd.groomingzone.member.application.port.in.MemberCommandResponse;
import tdd.groomingzone.member.application.port.in.usecase.PostMemberUseCase;

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

@WebMvcTest(controllers = PostMemberController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureRestDocs
class PostMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private PostMemberUseCase postMemberUseCase;

    @Test
    @DisplayName("정상적인 회원 가입 요청 테스트")
    void postMemberTest() throws Exception {
        // given
        String email = "email@email.com";
        String password = "11aA@!Password";
        String nickName = "nickName";
        String phoneNumber = "010-1111-1111";
        String role = "BARBER";

        MemberApiDto.Post postDto = MemberApiDto.Post.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .phoneNumber(phoneNumber)
                .role(role)
                .build();

        String content = gson.toJson(postDto);

        MemberCommandResponse commandResponse = MemberCommandResponse.of(1L, email, nickName, phoneNumber, role);

        given(postMemberUseCase.postMember(any())).willReturn(commandResponse);

        // when // then
        mockMvc.perform(
                        post("/member")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(postDto.getEmail()))
                .andExpect(jsonPath("$.nickName").value(postDto.getNickName()))
                .andExpect(jsonPath("$.phoneNumber").value(postDto.getPhoneNumber()))
                .andExpect(jsonPath("$.role").value(postDto.getRole()))
                .andDo(document(
                        "post-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀 번호"),
                                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("전화 번호"),
                                        fieldWithPath("role").type(JsonFieldType.STRING).description("역할")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("전화 번호"),
                                        fieldWithPath("role").type(JsonFieldType.STRING).description("역할")
                                )
                        )
                ));
    }
}