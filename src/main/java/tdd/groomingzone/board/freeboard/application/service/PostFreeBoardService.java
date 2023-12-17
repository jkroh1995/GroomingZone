package tdd.groomingzone.board.freeboard.application.service;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import tdd.groomingzone.board.common.WriterInfo;

import tdd.groomingzone.board.freeboard.application.port.in.PostFreeBoardCommand;
import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.PostFreeBoardUseCase;

import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardQueryResult;
import tdd.groomingzone.board.freeboard.application.port.out.SaveFreeBoardPort;

import tdd.groomingzone.board.freeboard.domain.FreeBoard;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

import java.util.ArrayList;

@Service
public class PostFreeBoardService implements PostFreeBoardUseCase {
    private final LoadMemberPort loadMemberPort;
    private final SaveFreeBoardPort saveFreeBoardPort;

    public PostFreeBoardService(LoadMemberPort loadMemberPort, SaveFreeBoardPort saveFreeBoardPort) {
        this.loadMemberPort = loadMemberPort;
        this.saveFreeBoardPort = saveFreeBoardPort;
    }

    @Override
    @Transactional
    public FreeBoardCommandResponse postFreeBoard(PostFreeBoardCommand command) {
        FreeBoard freeBoard = FreeBoard.builder()
                .writer(Member.builder().memberId(command.getWriter().getId()).build())
                .title(command.getTitle())
                .content(command.getContent())
                .build();
        FreeBoardQueryResult queryResult = saveFreeBoardPort.save(freeBoard);
        FreeBoard savedFreeBoard = queryResult.getFreeBoard();
        Member writer = loadMemberPort.findMemberById(command.getWriter().getId());

        return FreeBoardCommandResponse.of(savedFreeBoard.getId(),
                savedFreeBoard.getTitleValue(),
                savedFreeBoard.getContent(),
                savedFreeBoard.getViewCount(),
                savedFreeBoard.getCreatedAt(),
                savedFreeBoard.getModifiedAt(),
                WriterInfo.of(writer),
                new ArrayList<>());
    }
}
