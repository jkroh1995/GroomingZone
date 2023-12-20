package tdd.groomingzone.board.freeboard.application.service;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import tdd.groomingzone.board.common.WriterInfo;
import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardEntityCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.command.PutFreeBoardCommand;
import tdd.groomingzone.board.freeboard.application.port.in.usecase.PutFreeBoardUseCase;

import tdd.groomingzone.board.freeboard.application.port.out.*;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

@Service
public class PutFreeBoardService implements PutFreeBoardUseCase {
    private final LoadMemberPort loadMemberPort;
    private final LoadFreeBoardPort loadFreeBoardPort;
    private final SaveFreeBoardPort saveFreeBoardPort;

    public PutFreeBoardService(LoadMemberPort loadMemberPort, LoadFreeBoardPort loadFreeBoardPort, SaveFreeBoardPort saveFreeBoardPort) {
        this.loadMemberPort = loadMemberPort;
        this.loadFreeBoardPort = loadFreeBoardPort;
        this.saveFreeBoardPort = saveFreeBoardPort;
    }

    @Override
    @Transactional
    public FreeBoardEntityCommandResponse putFreeBoard(PutFreeBoardCommand command) {
        FreeBoardEntityQueryResult findQueryResult = loadFreeBoardPort.loadFreeBoardById(command.getFreeBoardId());
        Member writer = loadMemberPort.findMemberById(findQueryResult.getWriterId());
        FreeBoard freeBoard = findQueryResult.getFreeBoard();
        freeBoard.setWriter(writer);

        Member requestMember = loadMemberPort.findMemberById(command.getWriter().getId());
        freeBoard.checkMemberAuthority(requestMember);
        freeBoard.modify(command.getTitle(), command.getContent(), command.getModifiedAt());

        FreeBoardEntityQueryResult savedQueryResult = saveFreeBoardPort.save(freeBoard);
        FreeBoard updatedFreeBoard = savedQueryResult.getFreeBoard();
        return FreeBoardEntityCommandResponse.of(updatedFreeBoard.getId(),
                updatedFreeBoard.getTitleValue(),
                updatedFreeBoard.getContent(),
                updatedFreeBoard.getViewCount(),
                updatedFreeBoard.getCreatedAt(),
                updatedFreeBoard.getModifiedAt(),
                WriterInfo.of(writer));
    }
}