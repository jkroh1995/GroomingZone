package tdd.groomingzone.board.freeboard.application.service;

import org.springframework.stereotype.Service;

import tdd.groomingzone.board.common.WriterInfo;
import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.PutFreeBoardCommand;
import tdd.groomingzone.board.freeboard.application.port.in.PutFreeBoardUseCase;

import tdd.groomingzone.board.freeboard.application.port.out.*;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;

import tdd.groomingzone.member.LoadMemberPort;
import tdd.groomingzone.member.Member;

import java.util.ArrayList;

@Service
public class PutFreeBoardService implements PutFreeBoardUseCase {

    private final LoadFreeBoardPort loadFreeBoardPort;
    private final SaveFreeBoardPort saveFreeBoardPort;

    public PutFreeBoardService(LoadFreeBoardPort loadFreeBoardPort, SaveFreeBoardPort saveFreeBoardPort) {
        this.loadFreeBoardPort = loadFreeBoardPort;
        this.saveFreeBoardPort = saveFreeBoardPort;
    }

    @Override
    public FreeBoardCommandResponse putFreeBoard(PutFreeBoardCommand putFreeBoardCommand) {
        FreeBoard freeBoard = loadFreeBoardPort.loadFreeBoardById(putFreeBoardCommand.getFreeBoardId());
        freeBoard.checkMemberAuthority(putFreeBoardCommand.getWriter().getId());
        freeBoard.modify(putFreeBoardCommand.getTitle(), putFreeBoardCommand.getContent(), putFreeBoardCommand.getModifiedAt());
        FreeBoard savedFreeBoard = saveFreeBoardPort.save(putFreeBoardCommand.getWriter().getId(), freeBoard);
        return FreeBoardCommandResponse.of(savedFreeBoard.getId(),
                savedFreeBoard.getTitle(),
                savedFreeBoard.getContent(),
                savedFreeBoard.getViewCount(),
                savedFreeBoard.getCreatedAt(),
                savedFreeBoard.getModifiedAt(),
                WriterInfo.of(putFreeBoardCommand.getWriter()),
                new ArrayList<>());
    }
}
