package tdd.groomingzone.auth.application.port.out;

public interface RedisSignInPort {
    void signIn(String email, String accessToken);

    boolean isMemberAlreadySignIn(String email);
}
