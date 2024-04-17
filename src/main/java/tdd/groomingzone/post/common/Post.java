package tdd.groomingzone.post.common;

import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

public interface Post {

    void checkMemberAuthority(Member member);

    void modify(String title, String content, LocalDateTime modifiedAt);
}
