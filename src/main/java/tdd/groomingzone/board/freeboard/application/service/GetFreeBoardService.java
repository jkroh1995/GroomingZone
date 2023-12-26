package tdd.groomingzone.board.freeboard.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tdd.groomingzone.board.common.WriterInfo;
import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardEntityCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.command.GetFreeBoardCommand;
import tdd.groomingzone.board.freeboard.application.port.in.usecase.GetFreeBoardUseCase;
import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.board.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.board.freeboard.application.port.out.SaveFreeBoardPort;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;

import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

@Service
public class GetFreeBoardService implements GetFreeBoardUseCase {

    private final LoadFreeBoardPort loadFreeBoardPort;
    private final SaveFreeBoardPort saveFreeBoardPort;
    private final LoadMemberPort loadMemberPort;

    public GetFreeBoardService(LoadFreeBoardPort loadFreeBoardPort, SaveFreeBoardPort saveFreeBoardPort, LoadMemberPort loadMemberPort) {
        this.loadFreeBoardPort = loadFreeBoardPort;
        this.saveFreeBoardPort = saveFreeBoardPort;
        this.loadMemberPort = loadMemberPort;
    }

    @Override
    @Transactional
    public FreeBoardEntityCommandResponse getFreeBoard(GetFreeBoardCommand getFreeBoardCommand) {
        FreeBoardEntityQueryResult selectQueryResult = loadFreeBoardPort.loadFreeBoardById(getFreeBoardCommand.getFreeBoardId());
        Member writer = loadMemberPort.findMemberById(selectQueryResult.getWriterId());
        FreeBoard freeBoard = FreeBoard.builder()
                .id(selectQueryResult.getId())
                .writer(writer)
                .title(selectQueryResult.getTitle())
                .content(selectQueryResult.getContent())
                .viewCount(selectQueryResult.getViewCount())
                .createdAt(selectQueryResult.getCreatedAt())
                .modifiedAt(selectQueryResult.getModifiedAt()).build();
        freeBoard.viewed();

        saveFreeBoardPort.save(freeBoard);

        return FreeBoardEntityCommandResponse.of(freeBoard.getId(),
                freeBoard.getTitleValue(),
                freeBoard.getContent(),
                freeBoard.getViewCount(),
                freeBoard.getCreatedAt(),
                freeBoard.getModifiedAt(),
                WriterInfo.of(writer));
    }
}
