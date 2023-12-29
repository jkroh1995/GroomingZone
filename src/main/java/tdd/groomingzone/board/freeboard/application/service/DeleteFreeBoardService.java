package tdd.groomingzone.board.freeboard.application.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.board.freeboard.application.port.in.command.DeleteFreeBoardCommand;
import tdd.groomingzone.board.freeboard.application.port.in.usecase.DeleteFreeBoardUseCase;
import tdd.groomingzone.board.freeboard.application.port.out.DeleteFreeBoardPort;
import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.board.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.board.freeboard.application.port.out.query.DeleteFreeBoardQuery;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

@Service
public class DeleteFreeBoardService implements DeleteFreeBoardUseCase {

    private final LoadMemberPort loadMemberPort;
    private final LoadFreeBoardPort loadFreeBoardPort;
    private final DeleteFreeBoardPort deleteFreeBoardPort;

    public DeleteFreeBoardService(LoadMemberPort loadMemberPort, LoadFreeBoardPort loadFreeBoardPort, DeleteFreeBoardPort deleteFreeBoardPort) {
        this.loadMemberPort = loadMemberPort;
        this.loadFreeBoardPort = loadFreeBoardPort;
        this.deleteFreeBoardPort = deleteFreeBoardPort;
    }

    @Override
    public void deleteFreeBoard(DeleteFreeBoardCommand command) {
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

        Member requestMember = loadMemberPort.findMemberById(command.getRequestMemberId());
        freeBoard.checkMemberAuthority(requestMember);

        DeleteFreeBoardQuery deleteFreeBoardQuery = DeleteFreeBoardQuery.of(freeBoard.getId());
        deleteFreeBoardPort.delete(deleteFreeBoardQuery);
    }
}
