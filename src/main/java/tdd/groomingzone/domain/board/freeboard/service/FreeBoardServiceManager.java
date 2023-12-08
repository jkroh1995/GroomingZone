package tdd.groomingzone.domain.board.freeboard.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.dto.FreeBoardDto;
import tdd.groomingzone.domain.board.freeboard.FreeBoardService;
import tdd.groomingzone.domain.member.entity.Member;
import tdd.groomingzone.global.pagedresponse.PagedResponseDto;

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
    public FreeBoardDto.Response postFreeBoard(Member writer, FreeBoardDto.Post postDto) {
        FreeBoard entity = freeBoardConverter.convertPostDtoToEntity(postDto);
        writer.writeFreeBoard(entity);
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
    @Transactional
    public FreeBoardDto.Response getFreeBoard(long id) {
        FreeBoard entity = freeBoardQueryService.readEntityById(id);
        entity.viewed();
        return freeBoardConverter.convertEntityToResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponseDto<FreeBoardDto.Response> getFreeBoardPage(int pageNumber) {
        verifyPageNumber(pageNumber);
        int pageIndex = pageNumber - 1;
        Page<FreeBoard> page = freeBoardQueryService.readPagedEntity(pageIndex);
        List<FreeBoardDto.Response> responseList = page.stream()
                .map(freeBoardConverter::convertEntityToResponseDto)
                .collect(Collectors.toList());
        return new PagedResponseDto<>(responseList, page);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponseDto<FreeBoardDto.Response> getFilteredFreeBoardList(String title, String content, String writer, int pageNumber) {
        verifyPageNumber(pageNumber);
        int pageIndex = pageNumber-1;
        Page<FreeBoard> page = freeBoardQueryService.readFilteredEntityPage(title, content, writer, pageIndex);
        List<FreeBoardDto.Response> responseList =  page.stream()
                .map(freeBoardConverter::convertEntityToResponseDto)
                .collect(Collectors.toList());
        return new PagedResponseDto<>(responseList, page);
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