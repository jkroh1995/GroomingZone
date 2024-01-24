package tdd.groomingzone.auth.utils.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import tdd.groomingzone.auth.oauth2.CustomOAuth2User;
import tdd.groomingzone.auth.application.service.SuccessfulAuthenticationProcessor;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;
import tdd.groomingzone.member.adapter.out.persistence.repository.MemberEntitiyRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberEntitiyRepository memberEntitiyRepository;
    private final SuccessfulAuthenticationProcessor successfulAuthenticationProcessor;

    public OAuth2MemberSuccessHandler(MemberEntitiyRepository memberEntitiyRepository, SuccessfulAuthenticationProcessor successfulAuthenticationProcessor) {
        this.memberEntitiyRepository = memberEntitiyRepository;
        this.successfulAuthenticationProcessor = successfulAuthenticationProcessor;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getEmail();

        MemberEntity memberEntity = memberEntitiyRepository.findByEmail(email).orElseThrow(() ->
                new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));

        successfulAuthenticationProcessor.setCookieInResponseHeader(response, memberEntity);
    }
}
