package tdd.groomingzone.board.freeboard.application.service;

import org.springframework.stereotype.Service;

import tdd.groomingzone.board.common.WriterInfo;
import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardEntityCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardPageCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.command.GetFreeBoardPageCommand;
import tdd.groomingzone.board.freeboard.application.port.in.usecase.GetFreeBoardPageUseCase;

import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardPage;
import tdd.groomingzone.board.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardPageQueryResult;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;
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
    public FreeBoardPageCommandResponse getFreeBoardPage(GetFreeBoardPageCommand command) {
        FreeBoardPage freeBoardPage = new FreeBoardPage(command.getPageNumber());
        FreeBoardPageQueryResult selectQueryResult = loadFreeBoardPort.loadFreeBoardPage(command.getTitle(),
                command.getContent(),
                command.getWriterNickName(),
                freeBoardPage);

        List<FreeBoardEntityCommandResponse> pageResponse = selectQueryResult.getQueryResults().stream()
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
                   return FreeBoardEntityCommandResponse.of(freeBoard.getId(),
                           freeBoard.getTitle(),
                           freeBoard.getContent(),
                           freeBoard.getViewCount(),
                           freeBoard.getCreatedAt(),
                           freeBoard.getModifiedAt(),
                           WriterInfo.of(writer));
                })
                .collect(Collectors.toList());
        PageInfo pageInfo = PageInfo.of(convertPageIndexToPageNumber(selectQueryResult), selectQueryResult.getPageSize(), selectQueryResult.getTotalElements(), selectQueryResult.getTotalPages());

        return FreeBoardPageCommandResponse.of(pageResponse, pageInfo);
    }

    private static int convertPageIndexToPageNumber(FreeBoardPageQueryResult selectQueryResult) {
        return selectQueryResult.getPageIndex() + 1;
    }
}
