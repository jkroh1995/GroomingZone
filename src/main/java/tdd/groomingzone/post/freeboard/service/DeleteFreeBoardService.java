package tdd.groomingzone.post.freeboard.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;
import tdd.groomingzone.post.freeboard.dto.DeleteFreeBoardCommand;
import tdd.groomingzone.post.freeboard.repository.DeleteFreeBoardPort;
import tdd.groomingzone.post.freeboard.repository.LoadFreeBoardPort;

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
        FreeBoard freeBoard = loadFreeBoardPort.loadFreeBoardById(command.freeBoardId());
        Member requestMember = loadMemberPort.findMemberByEmail(command.requestMemberEmail());
        freeBoard.checkMemberAuthority(requestMember);
        deleteFreeBoardPort.delete(freeBoard.getId());
    }
}

