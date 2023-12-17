package tdd.groomingzone.board.common;

import lombok.Getter;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;

@Getter
public final class WriterInfo {
    private final long writerId;
    private final String writerNickName;

    private WriterInfo(long writerId, String writerNickName){
        this.writerId = writerId;
        this.writerNickName = writerNickName;
    }

    public static WriterInfo of(MemberEntity writer){
        return new WriterInfo(writer.getId(), writer.getNickName());
    }
}
