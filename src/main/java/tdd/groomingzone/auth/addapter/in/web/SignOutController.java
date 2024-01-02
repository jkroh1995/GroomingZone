package tdd.groomingzone.auth.addapter.in.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.auth.application.port.in.usecase.SignOutUseCase;


@RestController
@Slf4j
@RequestMapping("/auth/sign-out")
public class SignOutController {

    private final SignOutUseCase signOutUseCase;

    public SignOutController(SignOutUseCase signOutUseCase) {
        this.signOutUseCase = signOutUseCase;
    }

    @DeleteMapping
    public ResponseEntity<String> signOut(@RequestHeader("Authorization") String accessToken,
                                           @RequestHeader("Refresh") String refreshToken ) {
        signOutUseCase.signOut(accessToken, refreshToken);
        log.info("# sign out");
        return ResponseEntity.ok("Signed out successfully.");
    }
}

