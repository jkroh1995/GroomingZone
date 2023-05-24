package singleproject.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Email
    private String email;

    @NotBlank
    private String nickName;

    @NotBlank
    private String instagram;

    @NotBlank
    private String phone;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Category category;

    public enum Category{
        CUSTOMER("cutomer"),
        BARBER("barber");

        @Getter
        private String category;

        Category(String category) {
            this.category = category;
        }
    }
}
