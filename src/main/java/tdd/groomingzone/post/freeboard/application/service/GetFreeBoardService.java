package tdd.groomingzone.post.freeboard.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.post.freeboard.application.port.in.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.command.GetFreeBoardCommand;
import tdd.groomingzone.post.freeboard.application.port.in.usecase.GetFreeBoardUseCase;
import tdd.groomingzone.post.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.post.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.post.freeboard.application.port.out.SaveFreeBoardPort;
import tdd.groomingzone.post.freeboard.application.port.out.query.SaveFreeBoardQuery;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;

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
    public SingleFreeBoardCommandResponse getFreeBoard(GetFreeBoardCommand getFreeBoardCommand) {
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
                WriterInfo.of(writer.getMemberId(), writer.getNickName()));
    }
}
