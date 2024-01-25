package tdd.groomingzone.post.common;

import tdd.groomingzone.member.domain.Member;

public interface Post {

    void checkMemberAuthority(Member member);

    void modify(BoardInfo boardInfo);
}
