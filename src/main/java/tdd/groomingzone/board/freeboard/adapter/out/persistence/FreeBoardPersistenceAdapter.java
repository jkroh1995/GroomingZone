package tdd.groomingzone.board.freeboard.adapter.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import tdd.groomingzone.board.freeboard.adapter.out.persistence.entity.FreeBoardEntity;
import tdd.groomingzone.board.freeboard.adapter.out.persistence.repository.FreeBoardEntityRepository;
import tdd.groomingzone.board.freeboard.application.port.out.*;
import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardPage;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

@Repository
public class FreeBoardPersistenceAdapter implements SaveFreeBoardPort, LoadFreeBoardPort, DeleteFreeBoardPort {
    private final FreeBoardEntityRepository freeBoardEntityRepository;
    private final FreeBoardMapper freeBoardMapper;

    public FreeBoardPersistenceAdapter(FreeBoardEntityRepository freeBoardEntityRepository, FreeBoardMapper freeBoardMapper) {
        this.freeBoardEntityRepository = freeBoardEntityRepository;
        this.freeBoardMapper = freeBoardMapper;
    }

    @Override
    public FreeBoardEntityQueryResult save(FreeBoard freeBoard) {
        FreeBoardEntity databaseEntity = freeBoardMapper.mapToDatabaseEntity(freeBoard);
        FreeBoardEntity savedFreeBoardEntity =  freeBoardEntityRepository.save(databaseEntity);
        return freeBoardMapper.mapToQueryResult(savedFreeBoardEntity);
    }

    @Override
    public FreeBoardEntityQueryResult loadFreeBoardById(long freeBoardId) {
        FreeBoardEntity findFreeBoardEntity = freeBoardEntityRepository.findById(freeBoardId).orElseThrow(() ->
                new BusinessException(ExceptionCode.BOARD_NOT_FOUND));
        return freeBoardMapper.mapToQueryResult(findFreeBoardEntity);
    }

    @Override
    public FreeBoardPageQueryResult loadFreeBoardPage(String title, String content, String writerNickName, FreeBoardPage freeBoardPage) {
        Pageable pageable = freeBoardPage.getPageable();
        Page<FreeBoardEntity> page = freeBoardEntityRepository.findFilteredFreeBoards(title, content, writerNickName, pageable);
        return freeBoardMapper.mapToMultiQueryResult(page);
    }

    @Override
    public void delete(FreeBoard freeBoard) {
        FreeBoardEntity databaseEntity = freeBoardMapper.mapToDatabaseEntity(freeBoard);
        freeBoardEntityRepository.delete(databaseEntity);
    }
}
