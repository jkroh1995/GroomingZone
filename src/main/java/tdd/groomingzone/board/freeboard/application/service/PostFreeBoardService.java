package tdd.groomingzone.board.freeboard.application.service;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import tdd.groomingzone.board.common.WriterInfo;

import tdd.groomingzone.board.freeboard.application.port.in.command.PostFreeBoardCommand;
import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardEntityCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.usecase.PostFreeBoardUseCase;

import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.board.freeboard.application.port.out.SaveFreeBoardPort;

import tdd.groomingzone.board.freeboard.domain.FreeBoard;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

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
    public FreeBoardEntityCommandResponse postFreeBoard(PostFreeBoardCommand command) {
        Member writer = loadMemberPort.findMemberById(command.getWriterId());
        FreeBoard freeBoard = FreeBoard.builder()
                .writer(writer)
                .title(command.getTitle())
                .content(command.getContent())
                .build();

        FreeBoardEntityQueryResult queryResult = saveFreeBoardPort.save(freeBoard);

        FreeBoard savedFreeBoard = FreeBoard.builder()
                .id(queryResult.getId())
                .writer(writer)
                .title(queryResult.getTitle())
                .content(queryResult.getContent())
                .viewCount(queryResult.getViewCount())
                .createdAt(queryResult.getCreatedAt())
                .modifiedAt(queryResult.getModifiedAt())
                .build();

        return FreeBoardEntityCommandResponse.of(savedFreeBoard.getId(),
                savedFreeBoard.getTitleValue(),
                savedFreeBoard.getContent(),
                savedFreeBoard.getViewCount(),
                savedFreeBoard.getCreatedAt(),
                savedFreeBoard.getModifiedAt(),
                WriterInfo.of(writer));
    }
}
