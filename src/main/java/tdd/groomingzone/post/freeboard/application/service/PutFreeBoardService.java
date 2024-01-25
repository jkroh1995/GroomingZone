package tdd.groomingzone.post.freeboard.application.service;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import tdd.groomingzone.post.common.BoardInfo;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.post.freeboard.application.port.in.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.command.PutFreeBoardCommand;
import tdd.groomingzone.post.freeboard.application.port.in.usecase.PutFreeBoardUseCase;

import tdd.groomingzone.post.freeboard.application.port.out.*;
import tdd.groomingzone.post.freeboard.application.port.out.query.SaveFreeBoardQuery;
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
        FreeBoardEntityQueryResult selectQueryResult = loadFreeBoardPort.loadFreeBoardById(command.getFreeBoardId());
        Member writer = loadMemberPort.findMemberById(selectQueryResult.getWriterId());
        FreeBoard freeBoard = FreeBoard.builder()
                .id(selectQueryResult.getId())
                .writer(writer)
                .title(selectQueryResult.getTitle())
                .content(selectQueryResult.getContent())
                .viewCount(selectQueryResult.getViewCount())
                .createdAt(selectQueryResult.getCreatedAt())
                .modifiedAt(selectQueryResult.getModifiedAt()).build();

        Member requestMember = loadMemberPort.findMemberById(command.getWriterId());
        freeBoard.checkMemberAuthority(requestMember);
        freeBoard.modify(BoardInfo.builder()
                .title(command.getTitle())
                .content(command.getContent())
                .modifiedAt(command.getModifiedAt())
                .viewCount(freeBoard.getViewCount())
                .build());

        SaveFreeBoardQuery saveFreeBoardQuery = SaveFreeBoardQuery.of(
                writer.getMemberId(),
                writer.getNickName(),
                freeBoard.getId(),
                freeBoard.getTitle(),
                freeBoard.getContent(),
                freeBoard.getViewCount(),
                freeBoard.getCreatedAt(),
                freeBoard.getModifiedAt());

        saveFreeBoardPort.save(saveFreeBoardQuery);

        return SingleFreeBoardCommandResponse.of(freeBoard.getId(),
                freeBoard.getTitle(),
                freeBoard.getContent(),
                freeBoard.getViewCount(),
                freeBoard.getCreatedAt(),
                freeBoard.getModifiedAt(),
                WriterInfo.of(writer));
    }
}