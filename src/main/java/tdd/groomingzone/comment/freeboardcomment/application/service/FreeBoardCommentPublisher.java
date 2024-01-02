package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.springframework.stereotype.Component;
import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

@Component
public class FreeBoardCommentPublisher {

    public FreeBoardComment createFreeBoardComment(FreeBoardEntityQueryResult queryResult, Member freeBoardWriter, Member commentWriter, String content, LocalDateTime createdAt, LocalDateTime modifiedAt){
        FreeBoard freeBoard = FreeBoard.builder()
                .id(queryResult.getId())
                .writer(freeBoardWriter)
                .title(queryResult.getTitle())
                .content(queryResult.getContent())
                .createdAt(queryResult.getCreatedAt())
                .modifiedAt(queryResult.getModifiedAt())
                .build();

        return FreeBoardComment.builder()
                .writer(commentWriter)
                .freeBoard(freeBoard)
                .content(content)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }
}
