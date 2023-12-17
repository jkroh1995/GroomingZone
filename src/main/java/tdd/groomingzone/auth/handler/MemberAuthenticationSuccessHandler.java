package tdd.groomingzone.auth.handler;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import tdd.groomingzone.member.adapter.dto.MemberApiDto;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;

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
        MemberEntity memberEntity = (MemberEntity) authentication.getPrincipal();
        MemberApiDto.Response dto  = MemberApiDto.Response.builder()
                .email(memberEntity.getEmail())
                .nickName(memberEntity.getNickName())
                .role(memberEntity.getRoles().get(0))
                .phoneNumber(memberEntity.getPhoneNumber())
                .build();

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(gson.toJson(dto));

        log.info("# Authenticated successfully!");
    }
}
