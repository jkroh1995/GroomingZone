package tdd.groomingzone.board.freeboard.application.service;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import tdd.groomingzone.board.common.WriterInfo;

import tdd.groomingzone.board.freeboard.application.port.in.PostFreeBoardCommand;
import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.PostFreeBoardUseCase;

import tdd.groomingzone.board.freeboard.application.port.out.SaveFreeBoardPort;

import tdd.groomingzone.board.freeboard.domain.FreeBoard;

import java.util.ArrayList;

@Service
public class PostFreeBoardService implements PostFreeBoardUseCase {
    private final SaveFreeBoardPort saveFreeBoardPort;

    public PostFreeBoardService(SaveFreeBoardPort saveFreeBoardPort) {
        this.saveFreeBoardPort = saveFreeBoardPort;
    }

    @Override
    @Transactional
    public FreeBoardCommandResponse postFreeBoard(PostFreeBoardCommand command) {
        FreeBoard freeBoard = FreeBoard.builder()
                .title(command.getTitle())
                .content(command.getContent())
                .build();
        FreeBoard savedFreeBoard = saveFreeBoardPort.save(command.getWriter().getId(), freeBoard);
        return FreeBoardCommandResponse.of(savedFreeBoard.getId(),
                savedFreeBoard.getTitleValue(),
                savedFreeBoard.getContent(),
                savedFreeBoard.getViewCount(),
                savedFreeBoard.getCreatedAt(),
                savedFreeBoard.getModifiedAt(),
                WriterInfo.of(command.getWriter()),
                new ArrayList<>());
    }
}