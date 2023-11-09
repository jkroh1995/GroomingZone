package tdd.groomingzone.board;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FreeBoardService {

    private final FreeBoardConverter freeBoardConverter;
    private final FreeBoardCommandService freeBoardCommandService;

    public FreeBoardService(FreeBoardConverter freeBoardConverter, FreeBoardCommandService freeBoardCommandService) {
        this.freeBoardConverter = freeBoardConverter;
        this.freeBoardCommandService = freeBoardCommandService;
    }

    @Transactional
    public FreeBoardDto.Response createFreeBoard(FreeBoardDto.Post postDto) {
        FreeBoard entity = freeBoardConverter.convertPostDtoToEntity(postDto);
        FreeBoard savedEntity = freeBoardCommandService.save(entity);
        return freeBoardConverter.convertEntityToResponseDto(savedEntity);
    }
}
