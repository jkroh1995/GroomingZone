package tdd.groomingzone.board.freeboard.adapter.out.persistence;

import org.springframework.stereotype.Component;

import tdd.groomingzone.board.freeboard.adapter.out.persistence.entity.FreeBoardEntity;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;
import tdd.groomingzone.member.domain.Member;

@Component
public class FreeBoardMapper {
    public FreeBoardEntity mapToDatabaseEntity(long writerId, FreeBoard freeBoard) {
        return FreeBoardEntity.builder()
                .boardId(freeBoard.getId())
                .memberId(writerId)
                .title(freeBoard.getTitleValue())
                .content(freeBoard.getContent())
                .build();
    }

    public FreeBoard mapToDomainEntity(Member writer, FreeBoardEntity freeBoardDatabaseEntity) {
        return FreeBoard.builder()
                .id(freeBoardDatabaseEntity.getId())
                .writer(writer)
                .title(freeBoardDatabaseEntity.getTitle())
                .content(freeBoardDatabaseEntity.getContent())
                .viewCount(freeBoardDatabaseEntity.getViewCount())
                .createdAt(freeBoardDatabaseEntity.getCreatedAt())
                .modifiedAt(freeBoardDatabaseEntity.getModifiedAt())
                .build();
    }
}
