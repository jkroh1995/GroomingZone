package tdd.groomingzone.post.freeboard.application.port.in;

import lombok.Data;
import tdd.groomingzone.global.pagedresponse.PageInfo;

import java.util.List;

@Data
public final class MultiFreeBoardCommandResponse {
    private final List<SingleFreeBoardCommandResponse> pageResponse;
    private final PageInfo pageInfo;

    private MultiFreeBoardCommandResponse(List<SingleFreeBoardCommandResponse> pageResponse, PageInfo pageInfo) {
        this.pageResponse = pageResponse;
        this.pageInfo = pageInfo;
    }

    public static MultiFreeBoardCommandResponse of(List<SingleFreeBoardCommandResponse> pageResponse, PageInfo pageInfo) {
        return new MultiFreeBoardCommandResponse(pageResponse, pageInfo);
    }
}
