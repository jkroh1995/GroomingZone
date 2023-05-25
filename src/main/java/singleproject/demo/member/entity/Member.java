package singleproject.demo.member.entity;

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

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String nickname;

    @NotBlank
    private String instagram;

    @NotBlank
    private String phone;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Category category;

    public enum Category{
        CUSTOMER("customer"),
        BARBER("barber");

        @Getter
        private final String category;

        Category(String category) {
            this.category = category;
        }
    }
}
