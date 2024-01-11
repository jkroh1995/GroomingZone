package tdd.groomingzone.post.freeboard.application.port.out;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public final class FreeBoardEntityQueryResult {
    private final Long id;
    private final String title;
    private final String content;
    private final int viewCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final long writerId;

    private FreeBoardEntityQueryResult(Long id, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, long writerId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.writerId = writerId;
    }

    public static FreeBoardEntityQueryResult of(Long id, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, long writerId){
        return new FreeBoardEntityQueryResult(id, title, content, viewCount, createdAt, modifiedAt, writerId);
    }
}