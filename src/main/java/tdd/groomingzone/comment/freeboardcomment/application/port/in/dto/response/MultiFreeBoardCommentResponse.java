package tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response;

import lombok.Getter;
import tdd.groomingzone.global.pagedresponse.PageInfo;

import java.util.List;

@Getter
public final class MultiFreeBoardCommentResponse {
    private final List<SingleFreeBoardCommentResponse> pageResponse;
    private final PageInfo pageInfo;

    private MultiFreeBoardCommentResponse(List<SingleFreeBoardCommentResponse> pageResponse, PageInfo pageInfo) {
        this.pageResponse = pageResponse;
        this.pageInfo = pageInfo;
    }

    public static MultiFreeBoardCommentResponse of(List<SingleFreeBoardCommentResponse> pageResponse, PageInfo pageInfo) {
        return new MultiFreeBoardCommentResponse(pageResponse, pageInfo);
    }
}
