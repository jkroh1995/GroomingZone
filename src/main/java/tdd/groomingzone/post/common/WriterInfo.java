package tdd.groomingzone.post.common;

import lombok.Getter;
import tdd.groomingzone.member.domain.Member;

@Getter
public final class WriterInfo {
    private final long writerId;
    private final String writerNickName;

    private WriterInfo(long writerId, String writerNickName){
        this.writerId = writerId;
        this.writerNickName = writerNickName;
    }

    public static WriterInfo of(long writerId, String writerNickName){
        return new WriterInfo(writerId, writerNickName);
    }
}
