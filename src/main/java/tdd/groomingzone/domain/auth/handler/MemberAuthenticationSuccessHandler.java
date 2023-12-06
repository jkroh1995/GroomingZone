package tdd.groomingzone.domain.auth.handler;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import tdd.groomingzone.domain.member.dto.MemberDto;
import tdd.groomingzone.domain.member.entity.Member;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Gson gson = new Gson();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request
            , HttpServletResponse response
            , Authentication authentication) throws IOException, ServletException {
        Member member = (Member) authentication.getPrincipal();
        MemberDto.Response dto  = MemberDto.Response.builder()
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRoles().get(0))
                .phoneNumber(member.getPhoneNumber())
                .build();

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(gson.toJson(dto));

        log.info("# Authenticated successfully!");
    }
}
