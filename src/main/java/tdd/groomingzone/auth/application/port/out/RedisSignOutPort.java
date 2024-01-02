package tdd.groomingzone.auth.application.port.out;

public interface RedisSignOutPort {

    void signOut(String accessToken, String refreshToken);

    boolean isSignOut(String accessToken);
}
