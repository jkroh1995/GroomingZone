package tdd.groomingzone.board;

import org.springframework.stereotype.Service;

@Service
public class FreeBoardCommandService {

    private final FreeBoardRepository freeBoardRepository;

    public FreeBoardCommandService(FreeBoardRepository freeBoardRepository) {
        this.freeBoardRepository = freeBoardRepository;
    }

    public FreeBoard save(FreeBoard entity) {
        return freeBoardRepository.save(entity);
    }
}
