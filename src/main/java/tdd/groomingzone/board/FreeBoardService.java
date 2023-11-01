package tdd.groomingzone.board;

import org.springframework.stereotype.Service;

@Service
public class FreeBoardService {

    private final FreeBoardConverter freeBoardConverter;
    private final FreeBoardCommandService freeBoardCommandService;

    public FreeBoardService(FreeBoardConverter freeBoardConverter, FreeBoardCommandService freeBoardCommandService) {
        this.freeBoardConverter = freeBoardConverter;
        this.freeBoardCommandService = freeBoardCommandService;
    }

    public FreeBoardDto.Response createFreeBoard(FreeBoardDto.Post postDto) {
        FreeBoard entity = freeBoardConverter.convertPostDtoToEntity(postDto);
        FreeBoard savedEntity = freeBoardCommandService.save(entity);
        return null;
    }
}
