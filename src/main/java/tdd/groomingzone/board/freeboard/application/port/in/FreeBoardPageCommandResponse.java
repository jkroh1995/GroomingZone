package tdd.groomingzone.board.freeboard.application.port.in;

import lombok.Data;
import tdd.groomingzone.global.pagedresponse.PageInfo;

import java.util.List;

@Data
public final class FreeBoardPageCommandResponse {
    private final List<FreeBoardEntityCommandResponse> pageResponse;
    private final PageInfo pageInfo;

    private FreeBoardPageCommandResponse(List<FreeBoardEntityCommandResponse> pageResponse, PageInfo pageInfo) {
        this.pageResponse = pageResponse;
        this.pageInfo = pageInfo;
    }

    public static FreeBoardPageCommandResponse of(List<FreeBoardEntityCommandResponse> pageResponse, PageInfo pageInfo) {
        return new FreeBoardPageCommandResponse(pageResponse, pageInfo);
    }
}
