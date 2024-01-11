package tdd.groomingzone.board.recruitment.adapter.out.persistence;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tdd.groomingzone.board.common.BoardEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "RECRUITMENT")
public class RecruitmentEntity extends BoardEntity {

    @Column(name = "RECRUITMENT_TYPE")
    private String type;

    @Builder
    private RecruitmentEntity(Long boardId, Long writerId, String writerNickName, String type, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.setId(boardId);
        this.setWriterId(writerId);
        this.setWriterNickName(writerNickName);
        this.setTitle(title);
        this.setContent(content);
        this.setViewCount(viewCount);
        this.setCreatedAt(createdAt);
        this.setModifiedAt(modifiedAt);
        this.type = type;
    }
}
