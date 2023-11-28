package tdd.groomingzone.domain.board.freeboard.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.groomingzone.domain.board.freeboard.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.repository.FreeBoardRepository;

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
}
