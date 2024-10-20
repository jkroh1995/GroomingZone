package tdd.groomingzone.post.freeboard.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;
import tdd.groomingzone.post.freeboard.dto.GetFreeBoardCommand;
import tdd.groomingzone.post.freeboard.dto.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.repository.LoadFreeBoardPort;
import tdd.groomingzone.post.freeboard.repository.SaveFreeBoardPort;

@Service
public class GetFreeBoardService implements GetFreeBoardUseCase {

    private final LoadFreeBoardPort loadFreeBoardPort;
    private final SaveFreeBoardPort saveFreeBoardPort;

    public GetFreeBoardService(LoadFreeBoardPort loadFreeBoardPort, SaveFreeBoardPort saveFreeBoardPort) {
        this.loadFreeBoardPort = loadFreeBoardPort;
        this.saveFreeBoardPort = saveFreeBoardPort;
    }

    @Override
    @Transactional
    public SingleFreeBoardCommandResponse getFreeBoard(GetFreeBoardCommand command) {
        FreeBoard freeBoard = loadFreeBoardPort.loadFreeBoardById(command.freeBoardId());
        freeBoard.viewed();
        saveFreeBoardPort.save(freeBoard);

        return SingleFreeBoardCommandResponse.of(freeBoard.getId(),
                freeBoard.getTitle(),
                freeBoard.getContent(),
                freeBoard.getViewCount(),
                freeBoard.getCreatedAt(),
                freeBoard.getModifiedAt(),
                WriterInfo.of(freeBoard.getWriterId(), freeBoard.getWriterNickName()));
    }
}

