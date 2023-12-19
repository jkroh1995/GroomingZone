package tdd.groomingzone.board.freeboard.adapter.out.persistence;

import org.springframework.stereotype.Component;

import tdd.groomingzone.board.freeboard.adapter.out.persistence.entity.FreeBoardEntity;
import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardQueryResult;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;

@Component
public class FreeBoardMapper {
    public FreeBoardEntity mapToDatabaseEntity(FreeBoard freeBoard) {
        return FreeBoardEntity.builder()
                .boardId(freeBoard.getId())
                .memberId(freeBoard.getWriterId())
                .title(freeBoard.getTitleValue())
                .content(freeBoard.getContent())
                .build();
    }

    public FreeBoardQueryResult mapToQueryResult(FreeBoardEntity freeBoardDatabaseEntity) {
        FreeBoard freeBoard = FreeBoard.builder()
                .id(freeBoardDatabaseEntity.getId())
                .title(freeBoardDatabaseEntity.getTitle())
                .content(freeBoardDatabaseEntity.getContent())
                .viewCount(freeBoardDatabaseEntity.getViewCount())
                .createdAt(freeBoardDatabaseEntity.getCreatedAt())
                .modifiedAt(freeBoardDatabaseEntity.getModifiedAt())
                .build();
        return FreeBoardQueryResult.of(freeBoard, freeBoardDatabaseEntity.getMemberId());
    }
}
