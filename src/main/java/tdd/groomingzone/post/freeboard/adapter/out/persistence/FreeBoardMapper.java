package tdd.groomingzone.post.freeboard.adapter.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.freeboard.adapter.out.persistence.entity.FreeBoardEntity;
;
import tdd.groomingzone.post.freeboard.application.port.out.FreeBoardPage;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FreeBoardMapper {
    private final LoadMemberPort loadMemberPort;

    public FreeBoardMapper(LoadMemberPort loadMemberPort) {
        this.loadMemberPort = loadMemberPort;
    }

    public FreeBoardEntity mapToDatabaseEntity(FreeBoard freeBoard) {
        return FreeBoardEntity.builder()
                .boardId(freeBoard.getId())
                .writerId(freeBoard.getWriterId())
                .writerNickName(freeBoard.getWriterNickName())
                .title(freeBoard.getTitle())
                .content(freeBoard.getContent())
                .viewCount(freeBoard.getViewCount())
                .createdAt(freeBoard.getCreatedAt())
                .modifiedAt(freeBoard.getModifiedAt())
                .build();
    }

    public FreeBoard mapToDomainEntity(FreeBoardEntity freeBoardDatabaseEntity) {
        Member writer = loadMemberPort.findMemberById(freeBoardDatabaseEntity.getWriterId());
        return FreeBoard.builder()
                .writer(writer)
                .id(freeBoardDatabaseEntity.getId())
                .title(freeBoardDatabaseEntity.getTitle())
                .content(freeBoardDatabaseEntity.getContent())
                .viewCount(freeBoardDatabaseEntity.getViewCount())
                .createdAt(freeBoardDatabaseEntity.getCreatedAt())
                .modifiedAt(freeBoardDatabaseEntity.getModifiedAt())
                .build();
    }

    public FreeBoardPage mapToMultiQueryResult(Page<FreeBoardEntity> page) {
        List<FreeBoard> queryResults = page.getContent().stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());

        return FreeBoardPage.of(queryResults, page);
    }
}
