package tdd.groomingzone.post.freeboard.application.service;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.post.freeboard.application.port.in.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.command.PutFreeBoardCommand;
import tdd.groomingzone.post.freeboard.application.port.in.usecase.PutFreeBoardUseCase;

import tdd.groomingzone.post.freeboard.application.port.out.*;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;
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
    public SingleFreeBoardCommandResponse putFreeBoard(PutFreeBoardCommand command) {
        FreeBoard freeBoard = loadFreeBoardPort.loadFreeBoardById(command.freeBoardId());
        Member requestMember = loadMemberPort.findMemberByEmail(command.requestMemberEmail());
        freeBoard.checkMemberAuthority(requestMember);
        freeBoard.modify(command.title(), command.content(), command.modifiedAt());

        saveFreeBoardPort.save(freeBoard);

        return SingleFreeBoardCommandResponse.of(freeBoard.getId(),
                freeBoard.getTitle(),
                freeBoard.getContent(),
                freeBoard.getViewCount(),
                freeBoard.getCreatedAt(),
                freeBoard.getModifiedAt(),
                WriterInfo.of(freeBoard.getWriterId(), freeBoard.getWriterNickName()));
    }
}