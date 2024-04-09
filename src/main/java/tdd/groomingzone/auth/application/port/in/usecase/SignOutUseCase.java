package tdd.groomingzone.auth.application.port.in.usecase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface SignOutUseCase {
    void signOut(String email, HttpServletRequest request, HttpServletResponse response);
}
