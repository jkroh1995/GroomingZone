package tdd.groomingzone.domain.board.freeboard.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.groomingzone.domain.board.freeboard.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.FreeBoardDto;
import tdd.groomingzone.domain.board.freeboard.repository.FreeBoardRepository;

@Service
public class FreeBoardCommandService {

    private final FreeBoardRepository freeBoardRepository;

    public FreeBoardCommandService(FreeBoardRepository freeBoardRepository) {
        this.freeBoardRepository = freeBoardRepository;
    }

    @Transactional
    public FreeBoard create(FreeBoard entity) {
        return freeBoardRepository.save(entity);
    }

    @Transactional
    public void update(FreeBoard entity, FreeBoardDto.Put putDto) {
        entity.modify(putDto);
    }

    @Transactional
    public void delete(FreeBoard entity) {
        freeBoardRepository.delete(entity);
    }
}