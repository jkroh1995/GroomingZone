package tdd.groomingzone.auth.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import tdd.groomingzone.auth.application.port.in.usecase.SignOutUseCase;
import tdd.groomingzone.auth.utils.CookieManager;

import javax.servlet.http.Cookie;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static tdd.groomingzone.global.utils.ApiDocumentUtils.getResponsePreProcessor;

@WebMvcTest(controllers = {SignOutController.class},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureRestDocs
class SignOutControllerTest {

    private final CookieManager cookieManager = new CookieManager();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityFilterChain oauth2SecurityFilterChain;

    @MockBean
    private SignOutUseCase signOutUseCase;

    @Test
    void testSignOut() throws Exception {
        String testAccessToken = "test access token";
        String testRefreshToken = "test refresh token";

        Cookie accessTokenCookie = cookieManager.createCookie("ACCESS_TOKEN", testAccessToken, 1);
        Cookie refreshTokenCookie = cookieManager.createCookie("REFRESH_TOKEN", testRefreshToken, 1);

        mockMvc.perform(
                        delete("/auth/sign-out")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .cookie(accessTokenCookie, refreshTokenCookie)
                ).andExpect(status().isOk())
                .andDo(document(
                        "sign-out",
                        getRequestPreProcessor(),
                        getResponsePreProcessor()
                ));

        verify(signOutUseCase).signOut(any(), any(), any());
    }
}