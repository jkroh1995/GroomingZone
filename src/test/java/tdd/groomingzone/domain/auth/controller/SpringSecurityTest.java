package tdd.groomingzone.domain.auth.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import tdd.groomingzone.domain.auth.dto.SignInDto;
import tdd.groomingzone.domain.member.dto.MemberDto;
import tdd.groomingzone.domain.member.service.MemberServiceManager;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tdd.groomingzone.global.util.ApiDocumentUtils.getRequestPreProcessor;
import static tdd.groomingzone.global.util.ApiDocumentUtils.getResponsePreProcessor;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class SpringSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberServiceManager memberServiceManager;

    @Autowired
    private Gson gson;

    @BeforeEach
    void createUser() {
        String email = "jk@gmail.com";
        String password = "123";
        String name = "JKROH";
        String phoneNumber = "010-1111-2222";
        String role = "BARBER";

        MemberDto.Post dto = new MemberDto.Post();
        dto.email = email;
        dto.password = password;
        dto.name = name;
        dto.phoneNumber = phoneNumber;
        dto.role = role;

        memberServiceManager.postMember(dto);
    }

    @Test
    void login_success() throws Exception {
        String email = "jk@gmail.com";
        String password = "123";

        SignInDto signInDto = new SignInDto();
        signInDto.setEmail(email);
        signInDto.setPassword(password);

        String content = gson.toJson(signInDto);

        mockMvc.perform(
                        post("/auth/sign-in")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andExpect(header().exists("Refresh"))
                .andDo(document(
                        "sign-in",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                                )
                        )
                ));
    }
}