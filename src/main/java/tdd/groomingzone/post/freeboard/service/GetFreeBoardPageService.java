package tdd.groomingzone.post.freeboard.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.global.pagedresponse.PageInfo;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.common.PageNumber;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.post.freeboard.dto.GetFreeBoardPageCommand;
import tdd.groomingzone.post.freeboard.dto.MultiFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.dto.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.repository.FreeBoardPage;
import tdd.groomingzone.post.freeboard.repository.LoadFreeBoardPort;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetFreeBoardPageService implements GetFreeBoardPageUseCase {

    private final LoadFreeBoardPort loadFreeBoardPort;
    private final LoadMemberPort loadMemberPort;

    public GetFreeBoardPageService(LoadFreeBoardPort loadFreeBoardPort, LoadMemberPort loadMemberPort) {
        this.loadFreeBoardPort = loadFreeBoardPort;
        this.loadMemberPort = loadMemberPort;
    }

    @Override
    public MultiFreeBoardCommandResponse getFreeBoardPage(GetFreeBoardPageCommand command) {
        PageNumber pageNumber = new PageNumber(command.pageNumber());
        FreeBoardPage freeBoardPage = loadFreeBoardPort.loadFreeBoardPage(command.title(),
                command.content(),
                command.writerNickName(),
                pageNumber);

        List<SingleFreeBoardCommandResponse> pageResponse = freeBoardPage.freeBoards().stream()
                .map(freeBoard -> {
                    Member writer = loadMemberPort.findMemberById(freeBoard.getWriterId());
                    return SingleFreeBoardCommandResponse.of(freeBoard.getId(),
                            freeBoard.getTitle(),
                            freeBoard.getContent(),
                            freeBoard.getViewCount(),
                            freeBoard.getCreatedAt(),
                            freeBoard.getModifiedAt(),
                            WriterInfo.of(writer.getMemberId(), writer.getNickName()));
                })
                .collect(Collectors.toList());
        PageInfo pageInfo = PageInfo.of(pageNumber.pageNumber(), freeBoardPage.pageSize(), freeBoardPage.totalElements(), freeBoardPage.totalPages());

        return MultiFreeBoardCommandResponse.of(pageResponse, pageInfo);
    }
}
