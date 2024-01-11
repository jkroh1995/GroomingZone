package tdd.groomingzone.post.freeboard.adapter.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import tdd.groomingzone.post.freeboard.adapter.out.persistence.entity.FreeBoardEntity;
import tdd.groomingzone.post.freeboard.application.port.out.FreeBoardPageQueryResult;
import tdd.groomingzone.post.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.post.freeboard.application.port.out.query.SaveFreeBoardQuery;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FreeBoardMapper {
    public FreeBoardEntity mapToDatabaseEntity(SaveFreeBoardQuery query) {
        return FreeBoardEntity.builder()
                .boardId(query.getBoardId())
                .writerId(query.getWriterId())
                .writerNickName(query.getWriterNickName())
                .title(query.getTitle())
                .content(query.getContent())
                .createdAt(query.getCreatedAt())
                .modifiedAt(query.getModifiedAt())
                .build();
    }

    public FreeBoardEntityQueryResult mapToQueryResult(FreeBoardEntity freeBoardDatabaseEntity) {
        return FreeBoardEntityQueryResult.of(freeBoardDatabaseEntity.getId(),
                freeBoardDatabaseEntity.getTitle(),
                freeBoardDatabaseEntity.getContent(),
                freeBoardDatabaseEntity.getViewCount(),
                freeBoardDatabaseEntity.getCreatedAt(),
                freeBoardDatabaseEntity.getModifiedAt(),
                freeBoardDatabaseEntity.getWriterId());
    }

    public FreeBoardPageQueryResult mapToMultiQueryResult(Page<FreeBoardEntity> page) {
        List<FreeBoardEntityQueryResult> queryResults = page.getContent().stream()
                .map(this::mapToQueryResult)
                .collect(Collectors.toList());

        return FreeBoardPageQueryResult.of(queryResults, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}