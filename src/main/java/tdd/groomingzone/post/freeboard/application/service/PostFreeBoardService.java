package tdd.groomingzone.post.freeboard.application.service;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import tdd.groomingzone.post.common.WriterInfo;

import tdd.groomingzone.post.freeboard.application.port.in.command.PostFreeBoardCommand;
import tdd.groomingzone.post.freeboard.application.port.in.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.usecase.PostFreeBoardUseCase;

import tdd.groomingzone.post.freeboard.application.port.out.SaveFreeBoardPort;

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
    public SingleFreeBoardCommandResponse postFreeBoard(PostFreeBoardCommand command) {
        Member writer = loadMemberPort.findMemberByEmail(command.writerEmail());
        FreeBoard freeBoard = FreeBoard.builder()
                .id(CommonEnums.NEW_INSTANCE.getValue())
                .writer(writer)
                .title(command.title())
                .content(command.content())
                .viewCount(CommonEnums.NEW_INSTANCE.getValue())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        FreeBoard savedFreeBoard = saveFreeBoardPort.save(freeBoard);

        return SingleFreeBoardCommandResponse.of(savedFreeBoard.getId(),
                savedFreeBoard.getTitle(),
                savedFreeBoard.getContent(),
                savedFreeBoard.getViewCount(),
                savedFreeBoard.getCreatedAt(),
                savedFreeBoard.getModifiedAt(),
                WriterInfo.of(writer.getMemberId(), writer.getNickName()));
    }
}
