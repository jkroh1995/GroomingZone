package tdd.groomingzone.integrationtest;

import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import tdd.groomingzone.domain.auth.dto.SignInDto;
import tdd.groomingzone.domain.board.freeboard.dto.FreeBoardDto;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.service.FreeBoardCommandService;
import tdd.groomingzone.domain.member.entity.Member;
import tdd.groomingzone.domain.member.service.MemberCommandService;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tdd.groomingzone.global.util.ApiDocumentUtils.getRequestPreProcessor;
import static tdd.groomingzone.global.util.ApiDocumentUtils.getResponsePreProcessor;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration-test")
class SpringSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberCommandService memberCommandService;

    @Autowired
    private FreeBoardCommandService freeBoardCommandService;

    @Autowired
    private Gson gson;

    @BeforeAll
    void setUp(){
        String email = "jk@gmail.com";
        String password = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode( "123");
        String name = "JKROH";
        String phoneNumber = "010-1111-2222";

        Member member = Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .roles(List.of("BARBER", "CUSTOMER"))
                .build();

        Member createdMember = memberCommandService.createMember(member);

        FreeBoard freeBoard = FreeBoard.builder()
                .title("title")
                .content("content")
                .build();

        createdMember.writeFreeBoard(freeBoard);
        freeBoardCommandService.create(freeBoard);
    }

    @Test
    @DisplayName("등록된 사용자가 로그인 요청을 보내면 성공한다")
    void testSignIn() throws Exception {
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

    @Test
    @DisplayName("인증되지 않은 사용자가 인증을 요구하지 않는 요청을 보내면 성공한다.")
    @WithAnonymousUser
    void testUnauthorizedRequestUnauthorizedMethod() throws Exception {
        mockMvc.perform(
                        get("/free-board")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("인증되지 않은 사용자가 인증을 요구하는 요청을 보내면 실패한다.")
    @WithAnonymousUser
    void testUnauthorizedUserRequestAuthorizedMethod() throws Exception {
        String testTitle = "title";
        String testContent = "content";

        FreeBoardDto.Post testPost = new FreeBoardDto.Post();
        testPost.title = testTitle;
        testPost.content = testContent;

        String content = gson.toJson(testPost);
        mockMvc.perform(
                post("/free-board")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("인증된 사용자가 인증을 요구하는 요청을 보내면 성공한다.")
    @WithUserDetails(value = "jk@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void testAuthorizedUserRequestAuthorizedMethod() throws Exception {
        String testTitle = "title";
        String testContent = "content";

        FreeBoardDto.Post testPost = new FreeBoardDto.Post();
        testPost.title = testTitle;
        testPost.content = testContent;

        String content = gson.toJson(testPost);
        mockMvc.perform(
                post("/free-board")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(status().isCreated());
    }
}