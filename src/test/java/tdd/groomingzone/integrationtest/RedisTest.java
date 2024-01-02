package tdd.groomingzone.integrationtest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import tdd.groomingzone.auth.utils.JwtManager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getResponsePreProcessor;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RedisTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtManager jwtManager;

    private String accessToken;

    private String refreshToken;

    @BeforeAll
    void setUp() {
        String email = "jk@gmail.com";
        List<String>roles = List.of("BARBER", "CUSTOMER");

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("roles", roles);

        Date expiration = jwtManager.getTokenExpiration(jwtManager.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtManager.encodeBase64SecretKey(jwtManager.getSecretKey());

        accessToken = jwtManager.generateAccessToken(claims, email, expiration, base64EncodedSecretKey);
        refreshToken = jwtManager.generateRefreshToken(email, expiration, base64EncodedSecretKey);
    }

    @Test
    @DisplayName("인증되지 않은 사용자가 인증을 요구하지 않는 요청을 보내면 성공한다.")
    @WithMockUser
    void testUnauthorizedRequestUnauthorizedMethod() throws Exception {
        mockMvc.perform(
                delete("/auth/sign-out")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", accessToken)
                        .header("Refresh", refreshToken)
        ).andExpect(status().isOk())
                .andDo(document(
                        "sign-out",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                List.of(
                                        headerWithName("Authorization").description("Access Token"),
                                        headerWithName("Refresh").description("Refresh Token")
                                )
                        )));

        mockMvc.perform(
                delete("/auth/sign-out")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", accessToken)
                        .header("Refresh", refreshToken)
        ).andExpect(status().isUnauthorized());
    }
}
