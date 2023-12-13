package tdd.groomingzone.board.freeboard.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardMapper;
import tdd.groomingzone.board.freeboard.application.port.out.SaveFreeBoardPort;

import tdd.groomingzone.board.freeboard.domain.FreeBoard;

@Repository
public class FreeBoardPersistenceAdapter implements SaveFreeBoardPort {

    private final FreeBoardEntityRepository freeBoardEntityRepository;
    private final FreeBoardMapper freeBoardMapper;

    public FreeBoardPersistenceAdapter(FreeBoardEntityRepository freeBoardEntityRepository, FreeBoardMapper freeBoardMapper) {
        this.freeBoardEntityRepository = freeBoardEntityRepository;
        this.freeBoardMapper = freeBoardMapper;
    }


    @Override
    public FreeBoard save(FreeBoard freeBoard) {
        FreeBoardEntity freeBoardEntity = freeBoardMapper.mapToDatabaseEntity(freeBoard);
        FreeBoardEntity savedFreeBoardEntity =  freeBoardEntityRepository.save(freeBoardEntity);
        return freeBoardMapper.mapToDomainEntity(freeBoard, savedFreeBoardEntity);
    }
}
