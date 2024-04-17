package tdd.groomingzone.post.freeboard.application.port.in;

import tdd.groomingzone.global.pagedresponse.PageInfo;

import java.util.List;

public record MultiFreeBoardCommandResponse(
        List<SingleFreeBoardCommandResponse> pageResponse,
        PageInfo pageInfo

)  {
    public static MultiFreeBoardCommandResponse of(List<SingleFreeBoardCommandResponse> pageResponse, PageInfo pageInfo) {
        return new MultiFreeBoardCommandResponse(pageResponse, pageInfo);
    }
}
