package tdd.groomingzone.post.freeboard.application.service;

import org.springframework.stereotype.Service;

import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.post.freeboard.application.port.in.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.MultiFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.command.GetFreeBoardPageCommand;
import tdd.groomingzone.post.freeboard.application.port.in.usecase.GetFreeBoardPageUseCase;

import tdd.groomingzone.post.freeboard.application.port.out.FreeBoardPage;
import tdd.groomingzone.post.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.global.pagedresponse.PageInfo;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

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
