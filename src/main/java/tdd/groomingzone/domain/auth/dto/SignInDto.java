package tdd.groomingzone.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInDto {
    private String email;
    private String password;
}
