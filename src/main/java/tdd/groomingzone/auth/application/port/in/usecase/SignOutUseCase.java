package tdd.groomingzone.auth.application.port.in.usecase;

public interface SignOutUseCase {
    void signOut(String tokenHeader, String refreshToken);
}
