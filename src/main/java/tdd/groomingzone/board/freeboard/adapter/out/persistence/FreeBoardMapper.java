package tdd.groomingzone.board.freeboard.adapter.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import tdd.groomingzone.board.freeboard.adapter.out.persistence.entity.FreeBoardEntity;
import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardPageQueryResult;
import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FreeBoardMapper {
    public FreeBoardEntity mapToDatabaseEntity(FreeBoard freeBoard) {
        return FreeBoardEntity.builder()
                .boardId(freeBoard.getId())
                .writerId(freeBoard.getWriterId())
                .writerNickName(freeBoard.getWriterNickName())
                .title(freeBoard.getTitleValue())
                .content(freeBoard.getContent())
                .build();
    }

    public FreeBoardEntityQueryResult mapToQueryResult(FreeBoardEntity freeBoardDatabaseEntity) {
        FreeBoard freeBoard = FreeBoard.builder()
                .id(freeBoardDatabaseEntity.getId())
                .title(freeBoardDatabaseEntity.getTitle())
                .content(freeBoardDatabaseEntity.getContent())
                .viewCount(freeBoardDatabaseEntity.getViewCount())
                .createdAt(freeBoardDatabaseEntity.getCreatedAt())
                .modifiedAt(freeBoardDatabaseEntity.getModifiedAt())
                .build();
        return FreeBoardEntityQueryResult.of(freeBoard, freeBoardDatabaseEntity.getWriterId());
    }

    public FreeBoardPageQueryResult mapToMultiQueryResult(Page<FreeBoardEntity> page) {
        List<FreeBoardEntityQueryResult> queryResults = page.getContent().stream()
                .map(this::mapToQueryResult)
                .collect(Collectors.toList());

        return FreeBoardPageQueryResult.of(queryResults, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
