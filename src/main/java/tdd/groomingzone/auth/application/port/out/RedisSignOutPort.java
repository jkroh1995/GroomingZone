package tdd.groomingzone.auth.application.port.out;

public interface RedisSignOutPort {

    void signOut(String accessToken, String email);
}
