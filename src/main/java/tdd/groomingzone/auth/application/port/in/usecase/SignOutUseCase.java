package tdd.groomingzone.auth.application.port.in.usecase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SignOutUseCase {
    void signOut(String email, HttpServletRequest request, HttpServletResponse response);
}
