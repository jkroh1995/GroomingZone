package tdd.groomingzone.board.freeboard.adapter.out.persistence;

import org.springframework.stereotype.Component;

import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardQueryResult;
import tdd.groomingzone.board.freeboard.application.port.out.SaveFreeBoardQuery;

@Component
public class FreeBoardMapper {
    public FreeBoardEntity mapToDatabaseEntity(SaveFreeBoardQuery saveFreeBoardQuery) {
        return FreeBoardEntity.builder()
                .memberId(saveFreeBoardQuery.getWriterId())
                .title(saveFreeBoardQuery.getTitle())
                .content(saveFreeBoardQuery.getContent())
                .build();
    }

    public FreeBoardQueryResult mapToQueryResult(FreeBoardEntity savedFreeBoardEntity) {
        return FreeBoardQueryResult.of(savedFreeBoardEntity.getMemberId(),
                savedFreeBoardEntity.getId(),
                savedFreeBoardEntity.getTitle(),
                savedFreeBoardEntity.getContent(),
                savedFreeBoardEntity.getCreatedAt(),
                savedFreeBoardEntity.getModifiedAt(),
                savedFreeBoardEntity.getViewCount());
    }
}
