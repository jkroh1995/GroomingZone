package tdd.groomingzone.post.freeboard.application.service;

import org.springframework.stereotype.Service;

import tdd.groomingzone.member.PageNumber;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.post.freeboard.application.port.in.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.MultiFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.command.GetFreeBoardPageCommand;
import tdd.groomingzone.post.freeboard.application.port.in.usecase.GetFreeBoardPageUseCase;

import tdd.groomingzone.post.freeboard.application.port.out.FreeBoardPage;
import tdd.groomingzone.post.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;
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
        PageNumber pageNumber = new PageNumber(command.getPageNumber());
        FreeBoardPage freeBoardPage = loadFreeBoardPort.loadFreeBoardPage(command.getTitle(),
                command.getContent(),
                command.getWriterNickName(),
                pageNumber);

        List<SingleFreeBoardCommandResponse> pageResponse = freeBoardPage.getFreeBoards().stream()
                .map(eachQueryResult -> {
                    Member writer = loadMemberPort.findMemberById(eachQueryResult.getWriterId());
                    FreeBoard freeBoard = FreeBoard.builder()
                            .id(eachQueryResult.getId())
                            .writer(writer)
                            .title(eachQueryResult.getTitle())
                            .content(eachQueryResult.getContent())
                            .viewCount(eachQueryResult.getViewCount())
                            .createdAt(eachQueryResult.getCreatedAt())
                            .modifiedAt(eachQueryResult.getModifiedAt())
                            .build();
                   return SingleFreeBoardCommandResponse.of(freeBoard.getId(),
                           freeBoard.getTitle(),
                           freeBoard.getContent(),
                           freeBoard.getViewCount(),
                           freeBoard.getCreatedAt(),
                           freeBoard.getModifiedAt(),
                           WriterInfo.of(writer.getMemberId(), writer.getNickName()));
                })
                .collect(Collectors.toList());
        PageInfo pageInfo = PageInfo.of(pageNumber.getPageNumber(), freeBoardPage.getPageSize(), freeBoardPage.getTotalElements(), freeBoardPage.getTotalPages());

        return MultiFreeBoardCommandResponse.of(pageResponse, pageInfo);
    }
}
