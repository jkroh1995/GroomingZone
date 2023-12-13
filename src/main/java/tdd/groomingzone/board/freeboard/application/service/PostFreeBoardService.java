package tdd.groomingzone.board.freeboard.application.service;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import tdd.groomingzone.board.common.WriterInfo;

import tdd.groomingzone.board.freeboard.application.port.in.PostFreeBoardCommand;
import tdd.groomingzone.board.freeboard.application.port.in.PostFreeBoardResponse;
import tdd.groomingzone.board.freeboard.application.port.in.PostFreeBoardUseCase;

import tdd.groomingzone.board.freeboard.application.port.out.SaveFreeBoardPort;

import tdd.groomingzone.board.freeboard.application.port.out.SaveFreeBoardQuery;
import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardQueryResult;

@Service
public class PostFreeBoardService implements PostFreeBoardUseCase {

    private final SaveFreeBoardPort saveFreeBoardPort;

    public PostFreeBoardService(SaveFreeBoardPort saveFreeBoardPort) {
        this.saveFreeBoardPort = saveFreeBoardPort;
    }

    @Override
    @Transactional
    public PostFreeBoardResponse postFreeBoard(PostFreeBoardCommand command) {
        SaveFreeBoardQuery saveFreeBoardQuery = SaveFreeBoardQuery.of(command.getWriter().getId(), command.getTitle(), command.getContent());
        FreeBoardQueryResult queryResult = saveFreeBoardPort.save(saveFreeBoardQuery);
        return PostFreeBoardResponse.of(queryResult.getBoardId(),
                queryResult.getTitle(),
                queryResult.getContent(),
                queryResult.getViewCount(),
                queryResult.getCreatedAt(),
                queryResult.getModifiedAt(),
                WriterInfo.of(command.getWriter()));
    }
}
