package tdd.groomingzone.auth.addapter.in.springsecurity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {
    private String email;
    private String password;
}
