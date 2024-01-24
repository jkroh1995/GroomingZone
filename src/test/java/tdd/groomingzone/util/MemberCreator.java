package tdd.groomingzone.util;

import tdd.groomingzone.member.domain.Member;

public class MemberCreator {

    public static Member createMember(){
        return Member.builder()
                .memberId(1L)
                .email("test@email.com")
                .password("11aA!!@@Password")
                .phoneNumber("010-1111-1111")
                .nickName("nickName")
                .role("BARBER")
                .provider("SERVER")
                .build();
    }
}
