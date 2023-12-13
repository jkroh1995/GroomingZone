package tdd.groomingzone.board.freeboard.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardQueryResult;
import tdd.groomingzone.board.freeboard.application.port.out.SaveFreeBoardPort;

import tdd.groomingzone.board.freeboard.application.port.out.SaveFreeBoardQuery;

@Repository
public class FreeBoardPersistenceAdapter implements SaveFreeBoardPort {

    private final FreeBoardEntityRepository freeBoardEntityRepository;
    private final FreeBoardMapper freeBoardMapper;

    public FreeBoardPersistenceAdapter(FreeBoardEntityRepository freeBoardEntityRepository, FreeBoardMapper freeBoardMapper) {
        this.freeBoardEntityRepository = freeBoardEntityRepository;
        this.freeBoardMapper = freeBoardMapper;
    }

    @Override
    public FreeBoardQueryResult save(SaveFreeBoardQuery query) {
        FreeBoardEntity freeBoardEntity = freeBoardMapper.mapToDatabaseEntity(query);
        FreeBoardEntity savedFreeBoardEntity =  freeBoardEntityRepository.save(freeBoardEntity);
        return freeBoardMapper.mapToQueryResult(savedFreeBoardEntity);
    }
}
