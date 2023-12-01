package tdd.groomingzone.domain.board.freeboard.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.repository.FreeBoardRepository;

import java.util.List;

import static tdd.groomingzone.domain.board.utils.BoardEnums.PAGE_SIZE;

@Service
public class FreeBoardQueryService {
    private final FreeBoardRepository freeBoardRepository;

    public FreeBoardQueryService(FreeBoardRepository freeBoardRepository) {
        this.freeBoardRepository = freeBoardRepository;
    }

    @Transactional(readOnly = true)
    public FreeBoard readEntityById(long id) {
        return freeBoardRepository.findById(id).orElseThrow(() ->
                new RuntimeException());
    }

    @Transactional(readOnly = true)
    public Page<FreeBoard> readPagedEntity(int pageIndex) {
        Pageable pageable = PageRequest.ofSize(PAGE_SIZE.getValue()).withPage(pageIndex);
        return freeBoardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<FreeBoard> readFilteredEntityPage(String title, String content, String writer, int pageIndex) {
        Pageable pageable = Pageable.ofSize(PAGE_SIZE.getValue()).withPage(pageIndex);
        return freeBoardRepository.findFilteredFreeBoards(title, content, writer, pageable);
    }
}
