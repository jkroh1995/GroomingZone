package tdd.groomingzone.member.domain;

import lombok.Getter;

@Getter
public final class MemberVO {
    private final Long memberId;

    private MemberVO(Long memberId) {
        this.memberId = memberId;
    }

    public static MemberVO of(Long memberId){
        return new MemberVO(memberId);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MemberVO memberVO){
            return this.memberId.equals(memberVO.memberId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return memberId.intValue();
    }
}
