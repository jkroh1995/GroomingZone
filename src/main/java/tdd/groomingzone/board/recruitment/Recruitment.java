package tdd.groomingzone.board.recruitment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tdd.groomingzone.board.common.BoardEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "RECRUITMENT")
public class Recruitment extends BoardEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "RECRUITMENT_TYPE")
    private Type type;

    @Builder
    public Recruitment(long writerId, String writerNickName, String type, String title, String content){
        this.setWriterId(writerId);
        this.setWriterNickName(writerNickName);
        this.type = Type.valueOf(type);
        this.setTitle(title);
        this.setContent(content);
    }

    @Getter
    public enum Type{
        OFFER("구인"),
        SEARCH("구직");

        private final String type;

        Type(String type) {
            this.type = type;
        }
    }
}
