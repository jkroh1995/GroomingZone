package tdd.groomingzone.domain.board.recruitment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tdd.groomingzone.domain.board.Board;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "RECRUITMENT")
public class Recruitment extends Board {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "RECRUITMENT_TYPE")
    private Type type;

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
