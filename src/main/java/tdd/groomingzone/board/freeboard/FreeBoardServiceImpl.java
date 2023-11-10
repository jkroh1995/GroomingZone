package tdd.groomingzone.board.freeboard;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FreeBoardServiceImpl implements FreeBoardService{

    private final FreeBoardConverter freeBoardConverter;
    private final FreeBoardCommandService freeBoardCommandService;
    private final FreeBoardQueryService freeBoardQueryService;

    public FreeBoardServiceImpl(FreeBoardConverter freeBoardConverter, FreeBoardCommandService freeBoardCommandService, FreeBoardQueryService freeBoardQueryService) {
        this.freeBoardConverter = freeBoardConverter;
        this.freeBoardCommandService = freeBoardCommandService;
        this.freeBoardQueryService = freeBoardQueryService;
    }

    @Override
    @Transactional
    public FreeBoardDto.Response postFreeBoard(FreeBoardDto.Post postDto){
        FreeBoard entity = freeBoardConverter.convertPostDtoToEntity(postDto);
        FreeBoard savedEntity = freeBoardCommandService.create(entity);
        return freeBoardConverter.convertEntityToResponseDto(savedEntity);
    }

    @Override
    @Transactional
    public FreeBoardDto.Response putFreeBoard(long id, FreeBoardDto.Put putDto) {
        FreeBoard entity = freeBoardQueryService.readEntityById(id);
        freeBoardCommandService.update(entity, putDto);
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public FreeBoardDto.Response getFreeBoard(long id){
        FreeBoard entity = freeBoardQueryService.readEntityById(id);
        return freeBoardConverter.convertEntityToResponseDto(entity);
    }
}
