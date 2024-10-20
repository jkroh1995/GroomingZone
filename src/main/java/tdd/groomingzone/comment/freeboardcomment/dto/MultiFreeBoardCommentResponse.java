package tdd.groomingzone.comment.freeboardcomment.dto;

import tdd.groomingzone.global.pagedresponse.PageInfo;

import java.util.List;

public record MultiFreeBoardCommentResponse(
        List<SingleFreeBoardCommentResponse> pageResponse,
        PageInfo pageInfo
) {
    public static MultiFreeBoardCommentResponse of(List<SingleFreeBoardCommentResponse> pageResponse, PageInfo pageInfo) {
        return new MultiFreeBoardCommentResponse(pageResponse, pageInfo);
    }
}
