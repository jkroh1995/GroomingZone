package tdd.groomingzone.comment.freeboardcomment.application.service;

import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.member.domain.Member;

public class FreeBoardCommentPublisher {

    public static FreeBoardComment createFreeBoardComment(FreeBoardEntityQueryResult queryResult, Member freeBoardWriter, Member commentWriter, String content){
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
                .build();
    }
}