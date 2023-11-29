package tdd.groomingzone.domain.board.freeboard.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.repository.FreeBoardRepository;

import java.util.List;

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
    public List<FreeBoard> readPagedEntity(int page) {
        Pageable pageable = PageRequest.ofSize(20).withPage(page);
        Page<FreeBoard>freeBoardPage = freeBoardRepository.findAll(pageable);
        return freeBoardPage.getContent();
    }
}
