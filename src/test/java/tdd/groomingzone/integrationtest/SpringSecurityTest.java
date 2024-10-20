package tdd.groomingzone.integrationtest;

import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import tdd.groomingzone.auth.adapter.in.springsecurity.SignInDto;
import tdd.groomingzone.post.freeboard.dto.FreeBoardApiDto;
import tdd.groomingzone.post.freeboard.repository.FreeBoardEntity;
import tdd.groomingzone.post.freeboard.dto.PostFreeBoardCommand;
import tdd.groomingzone.post.freeboard.service.PostFreeBoardUseCase;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;
import tdd.groomingzone.member.adapter.out.persistence.repository.MemberEntitiyRepository;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getResponsePreProcessor;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Tag("integration-test") // restdocs 적용을 위한 태그 제거
class SpringSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberEntitiyRepository memberEntitiyRepository;

    @Autowired
    private PostFreeBoardUseCase postFreeBoardUseCase;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private Gson gson;

    @BeforeAll
    void setUp(){
        redisTemplate.keys("*").stream().forEach(key -> {
            redisTemplate.delete(key);
        });
        String email = "jk@gmail.com";
        String password = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode( "123");
        String nickName = "JKROH";
        String phoneNumber = "010-1111-2222";

        MemberEntity memberEntity = MemberEntity.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .phoneNumber(phoneNumber)
                .roles(List.of("BARBER", "CUSTOMER"))
                .provider("SERVER")
                .build();

        MemberEntity createdMemberEntity = memberEntitiyRepository.save(memberEntity);

        FreeBoardEntity freeBoard = FreeBoardEntity.builder()
                .title("title")
                .content("content")
                .build();

        postFreeBoardUseCase.postFreeBoard(PostFreeBoardCommand.of(createdMemberEntity.getEmail(), freeBoard.getTitle(), freeBoard.getContent()));
    }

    @AfterAll
    void after(){
        redisTemplate.keys("*").stream().forEach(key -> {
            redisTemplate.delete(key);
        });
    }

    @Test
    @DisplayName("등록된 사용자가 로그인 요청을 보내면 성공한다")
    void testSignIn() throws Exception {
        String email = "jk@gmail.com";
        String password = "123";

        SignInDto signInDto = new SignInDto(email, password);

        String content = gson.toJson(signInDto);

        mockMvc.perform(
                        post("/auth/sign-in")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isOk())
                .andExpect(cookie().exists("ACCESS_TOKEN"))
                .andExpect(cookie().exists("REFRESH_TOKEN"))
                .andDo(document(
                        "sign-in",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("role").type(JsonFieldType.STRING).description("역할")
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

        FreeBoardApiDto.Post testPost = FreeBoardApiDto.Post.builder()
                .title(testTitle)
                .content(testContent)
                .build();

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

        FreeBoardApiDto.Post testPost = FreeBoardApiDto.Post.builder()
                .title(testTitle)
                .content(testContent)
                .build();

        String content = gson.toJson(testPost);

        mockMvc.perform(
                post("/free-board")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(status().isCreated());
    }
}