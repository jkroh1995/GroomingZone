package tdd.groomingzone.post.freeboard.application.service;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import tdd.groomingzone.post.common.WriterInfo;

import tdd.groomingzone.post.freeboard.application.port.in.command.PostFreeBoardCommand;
import tdd.groomingzone.post.freeboard.application.port.in.FreeBoardEntityCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.usecase.PostFreeBoardUseCase;

import tdd.groomingzone.post.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.post.freeboard.application.port.out.SaveFreeBoardPort;

import tdd.groomingzone.post.freeboard.application.port.out.query.SaveFreeBoardQuery;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;
import tdd.groomingzone.global.utils.CommonEnums;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

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
                .id(CommonEnums.NEW_INSTANCE.getValue())
                .writer(writer)
                .title(command.getTitle())
                .content(command.getContent())
                .viewCount(CommonEnums.NEW_INSTANCE.getValue())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        SaveFreeBoardQuery saveFreeBoardQuery = SaveFreeBoardQuery.of(writer.getMemberId(),
                writer.getNickName(),
                freeBoard.getId(),
                freeBoard.getTitle(),
                freeBoard.getContent(),
                freeBoard.getViewCount(),
                freeBoard.getCreatedAt(),
                freeBoard.getModifiedAt());

        FreeBoardEntityQueryResult queryResult = saveFreeBoardPort.save(saveFreeBoardQuery);

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
                savedFreeBoard.getTitle(),
                savedFreeBoard.getContent(),
                savedFreeBoard.getViewCount(),
                savedFreeBoard.getCreatedAt(),
                savedFreeBoard.getModifiedAt(),
                WriterInfo.of(writer));
    }
}
