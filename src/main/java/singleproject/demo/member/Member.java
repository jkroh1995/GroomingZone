package singleproject.demo.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import singleproject.demo.comment.Comment;
import singleproject.demo.member.dto.MemberDto;
import singleproject.demo.question.Question;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "member")
    private List<Question> questions;

    @OneToMany(mappedBy = "member")
    private List<Comment> comments;

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

    public MemberDto.Response memberToMemberResponseDto() {
        MemberDto.Response responseDto = new MemberDto.Response();
        responseDto.setMemberId(memberId);
        responseDto.setName(name);
        responseDto.setEmail(email);
        responseDto.setPhone(phone);

        return responseDto;
    }
}
