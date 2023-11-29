package tdd.groomingzone.domain.board.freeboard.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.dto.FreeBoardDto;
import tdd.groomingzone.domain.board.freeboard.FreeBoardService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static tdd.groomingzone.domain.board.utils.BoardEnums.MINIMUM_PAGE_NUMBER_VALUE;

@Service
public class FreeBoardServiceManager implements FreeBoardService {

    private final FreeBoardConverter freeBoardConverter;
    private final FreeBoardCommandService freeBoardCommandService;
    private final FreeBoardQueryService freeBoardQueryService;

    public FreeBoardServiceManager(FreeBoardConverter freeBoardConverter, FreeBoardCommandService freeBoardCommandService, FreeBoardQueryService freeBoardQueryService) {
        this.freeBoardConverter = freeBoardConverter;
        this.freeBoardCommandService = freeBoardCommandService;
        this.freeBoardQueryService = freeBoardQueryService;
    }

    @Override
    @Transactional
    public FreeBoardDto.Response postFreeBoard(FreeBoardDto.Post postDto) {
        FreeBoard entity = freeBoardConverter.convertPostDtoToEntity(postDto);
        FreeBoard savedEntity = freeBoardCommandService.create(entity);
        return freeBoardConverter.convertEntityToResponseDto(savedEntity);
    }

    @Override
    @Transactional
    public FreeBoardDto.Response putFreeBoard(long id, FreeBoardDto.Put putDto, LocalDateTime modifiedAt) {
        FreeBoard entity = freeBoardQueryService.readEntityById(id);
        freeBoardCommandService.update(entity, putDto, modifiedAt);
        return freeBoardConverter.convertEntityToResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public FreeBoardDto.Response getFreeBoard(long id) {
        FreeBoard entity = freeBoardQueryService.readEntityById(id);
        return freeBoardConverter.convertEntityToResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FreeBoardDto.Response> getFreeBoardPage(int pageNumber) {
        verifyPageNumber(pageNumber);
        int pageIndex = pageNumber - 1;
        List<FreeBoard> entityList = freeBoardQueryService.readPagedEntity(pageIndex);
        return entityList.stream()
                .map(freeBoardConverter::convertEntityToResponseDto)
                .collect(Collectors.toList());
    }

    private void verifyPageNumber(int pageNumber) {
        if (pageNumber < MINIMUM_PAGE_NUMBER_VALUE.getValue()) {
            throw new RuntimeException("");
        }
    }

    @Override
    @Transactional
    public void deleteFreeBoard(long id) {
        FreeBoard entity = freeBoardQueryService.readEntityById(id);
        freeBoardCommandService.delete(entity);
    }
}