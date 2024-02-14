package tdd.groomingzone.comment.freeboardcomment.adapter.out;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import tdd.groomingzone.comment.common.CommentEntity;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.post.freeboard.application.port.out.LoadFreeBoardPort;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FreeBoardCommentMapper {
    private final LoadFreeBoardPort loadFreeBoardPort;
    private final LoadMemberPort loadMemberPort;

    public FreeBoardCommentMapper(LoadFreeBoardPort loadFreeBoardPort, LoadMemberPort loadMemberPort) {
        this.loadFreeBoardPort = loadFreeBoardPort;
        this.loadMemberPort = loadMemberPort;
    }

    public CommentEntity mapToDatabaseEntity(FreeBoardComment freeBoardComment) {
        return CommentEntity.builder()
                .boardId(freeBoardComment.getBoardId())
                .writerId(freeBoardComment.getWriterId())
                .content(freeBoardComment.getContent())
                .createdAt(freeBoardComment.getCreatedAt())
                .modifiedAt(freeBoardComment.getModifiedAt())
                .build();
    }

    public FreeBoardCommentPage mapToPage(Page<CommentEntity> page) {
        List<FreeBoardComment> queryResults = page.getContent().stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());

        return FreeBoardCommentPage.of(queryResults, page);
    }

    public FreeBoardComment mapToDomainEntity(CommentEntity databaseEntity) {
        return FreeBoardComment.builder()
                .freeBoard(loadFreeBoardPort.loadFreeBoardById(databaseEntity.getBoardId()))
                .writer(loadMemberPort.findMemberById(databaseEntity.getWriterId()))
                .id(databaseEntity.getId())
                .content(databaseEntity.getContent())
                .createdAt(databaseEntity.getCreatedAt())
                .modifiedAt(databaseEntity.getModifiedAt())
                .build();
    }
}
