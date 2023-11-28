package tdd.groomingzone.domain.board.freeboard.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.groomingzone.domain.board.freeboard.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.FreeBoardDto;
import tdd.groomingzone.domain.board.freeboard.FreeBoardService;

@Service
public class FreeBoardServiceImpl implements FreeBoardService {

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
        return freeBoardConverter.convertEntityToResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public FreeBoardDto.Response getFreeBoard(long id){
        FreeBoard entity = freeBoardQueryService.readEntityById(id);
        return freeBoardConverter.convertEntityToResponseDto(entity);
    }

    @Override
    @Transactional
    public void deleteFreeBoard(long id) {
        FreeBoard entity = freeBoardQueryService.readEntityById(id);
        freeBoardCommandService.delete(entity);
    }
}
