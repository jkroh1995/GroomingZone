package tdd.groomingzone.board.freeboard.adapter.out.persistence;

import org.springframework.stereotype.Component;

import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardMapper;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;

@Component
public class FreeBoardMapperImpl implements FreeBoardMapper {
    public FreeBoardEntity mapToDatabaseEntity(FreeBoard freeBoard) {
        return FreeBoardEntity.builder()
                .memberId(freeBoard.getWriter().getId())
                .title(freeBoard.getTitle())
                .content(freeBoard.getContent())
                .build();
    }

    public FreeBoard mapToDomainEntity(FreeBoard freeBoard, FreeBoardEntity savedFreeBoardEntity) {
        return FreeBoard.builder()
                .id(savedFreeBoardEntity.getId())
                .writer(freeBoard.getWriter())
                .title(savedFreeBoardEntity.getTitle())
                .content(savedFreeBoardEntity.getContent())
                .createdAt(savedFreeBoardEntity.getCreatedAt())
                .modifiedAt(savedFreeBoardEntity.getModifiedAt())
                .viewCount(savedFreeBoardEntity.getViewCount())
                .build();
    }
}
