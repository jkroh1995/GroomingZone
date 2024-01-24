package tdd.groomingzone.auth.adapter.in.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.auth.application.port.in.usecase.SignOutUseCase;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@Slf4j
@RequestMapping("/auth/sign-out")
public class SignOutController {

    private final SignOutUseCase signOutUseCase;

    public SignOutController(SignOutUseCase signOutUseCase) {
        this.signOutUseCase = signOutUseCase;
    }

    @DeleteMapping
    public ResponseEntity<String> signOut(@AuthenticationPrincipal MemberEntity requestMember,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        signOutUseCase.signOut(requestMember.getEmail(), request, response);
        log.info("# sign out");
        return ResponseEntity.ok("Signed out successfully.");
    }
}

