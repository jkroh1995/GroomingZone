package tdd.groomingzone.member.adapter.out.persistence;

import lombok.*;
import tdd.groomingzone.global.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NICK_NAME")
    private String nickName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "MEMBER_ROLES",
            joinColumns = @JoinColumn(name = "MEMBER_ID")
    )
    private List<String> roles = new ArrayList<>();

    @Builder
    private MemberEntity(long id, String email, String password, String nickName, String phoneNumber, List<String>roles, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
        this.setCreatedAt(createdAt);
        this.setModifiedAt(modifiedAt);
    }
}