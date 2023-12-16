package tdd.groomingzone.board.common;

import lombok.Getter;
import tdd.groomingzone.member.entity.MemberEntity;

@Getter
public final class WriterInfo {
    private final long writerId;
    private final String writerName;

    private WriterInfo(long writerId, String writerName){
        this.writerId = writerId;
        this.writerName = writerName;
    }

    public static WriterInfo of(MemberEntity writer){
        return new WriterInfo(writer.getId(), writer.getNickName());
    }
}
