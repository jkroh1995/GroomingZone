package tdd.groomingzone.board.freeboard.adapter.out.persistence;

import org.springframework.stereotype.Component;

import tdd.groomingzone.board.freeboard.adapter.out.persistence.entity.FreeBoardEntity;
import tdd.groomingzone.board.freeboard.application.port.out.SingleFreeBoardQueryResult;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;

@Component
public class FreeBoardMapper {
    public FreeBoardEntity mapToDatabaseEntity(FreeBoard freeBoard) {
        return FreeBoardEntity.builder()
                .boardId(freeBoard.getId())
                .writerId(freeBoard.getWriterId())
                .title(freeBoard.getTitleValue())
                .content(freeBoard.getContent())
                .build();
    }

    public SingleFreeBoardQueryResult mapToQueryResult(FreeBoardEntity freeBoardDatabaseEntity) {
        FreeBoard freeBoard = FreeBoard.builder()
                .id(freeBoardDatabaseEntity.getId())
                .title(freeBoardDatabaseEntity.getTitle())
                .content(freeBoardDatabaseEntity.getContent())
                .viewCount(freeBoardDatabaseEntity.getViewCount())
                .createdAt(freeBoardDatabaseEntity.getCreatedAt())
                .modifiedAt(freeBoardDatabaseEntity.getModifiedAt())
                .build();
        return SingleFreeBoardQueryResult.of(freeBoard, freeBoardDatabaseEntity.getWriterId());
    }
}
