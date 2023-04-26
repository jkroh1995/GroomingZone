package singleproject.demo.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column(nullable = false, updatable = false)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 13)
    private String phone;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.ACTIVE;

    @OneToOne(mappedBy = "member", cascade = CascadeType.PERSIST)
    private Stamp stamp;

    public enum MemberStatus {
        ACTIVE("활동중"),
        SLEEP("휴면 계정"),
        QUIT("탈퇴");

        @Getter
        private final String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
}
