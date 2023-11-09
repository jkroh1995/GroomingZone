package tdd.groomingzone.board;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FreeBoardServiceImpl implements FreeBoardService{

    private final FreeBoardConverter freeBoardConverter;
    private final FreeBoardCommandService freeBoardCommandService;

    public FreeBoardServiceImpl(FreeBoardConverter freeBoardConverter, FreeBoardCommandService freeBoardCommandService) {
        this.freeBoardConverter = freeBoardConverter;
        this.freeBoardCommandService = freeBoardCommandService;
    }

    @Override
    @Transactional
    public FreeBoardDto.Response postFreeBoard(FreeBoardDto.Post postDto){
        FreeBoard entity = freeBoardConverter.convertPostDtoToEntity(postDto);
        FreeBoard savedEntity = freeBoardCommandService.create(entity);
        return freeBoardConverter.convertEntityToResponseDto(savedEntity);
    }

    @Override
    public FreeBoardDto.Response putFreeBoard(FreeBoardDto.Put putDto) {

        return null;
    }
}
