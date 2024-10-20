package tdd.groomingzone.post.freeboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.post.common.PageNumber;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;

import static tdd.groomingzone.global.utils.CommonEnums.PAGE_SIZE;

@Repository
public class FreeBoardPersistenceAdapter implements SaveFreeBoardPort, LoadFreeBoardPort, DeleteFreeBoardPort {
    private final FreeBoardEntityRepository freeBoardEntityRepository;
    private final FreeBoardMapper freeBoardMapper;

    public FreeBoardPersistenceAdapter(FreeBoardEntityRepository freeBoardEntityRepository, FreeBoardMapper freeBoardMapper) {
        this.freeBoardEntityRepository = freeBoardEntityRepository;
        this.freeBoardMapper = freeBoardMapper;
    }

    @Override
    public FreeBoard save(FreeBoard freeBoard) {
        FreeBoardEntity databaseEntity = freeBoardMapper.mapToDatabaseEntity(freeBoard);
        FreeBoardEntity savedFreeBoardEntity =  freeBoardEntityRepository.save(databaseEntity);
        return freeBoardMapper.mapToDomainEntity(savedFreeBoardEntity);
    }

    @Override
    public FreeBoard loadFreeBoardById(long freeBoardId) {
        FreeBoardEntity findFreeBoardEntity = freeBoardEntityRepository.findById(freeBoardId).orElseThrow(() ->
                new BusinessException(ExceptionCode.BOARD_NOT_FOUND));
        return freeBoardMapper.mapToDomainEntity(findFreeBoardEntity);
    }

    @Override
    public FreeBoardPage loadFreeBoardPage(String title, String content, String writerNickName, PageNumber pageNumber) {
        Pageable pageable = Pageable.ofSize(PAGE_SIZE.getValue()).withPage(pageNumber.pageNumber());
        Page<FreeBoardEntity> page = freeBoardEntityRepository.findFilteredFreeBoards(title, content, writerNickName, pageable);
        return freeBoardMapper.mapToMultiQueryResult(page);
    }

    @Override
    public void delete(long freeBoardId) {
        FreeBoardEntity databaseEntity = freeBoardEntityRepository.findById(freeBoardId).orElseThrow(() ->
                new BusinessException(ExceptionCode.BOARD_NOT_FOUND));
        freeBoardEntityRepository.delete(databaseEntity);
    }
}
