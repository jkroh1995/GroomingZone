package tdd.groomingzone.board.freeboard.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import tdd.groomingzone.board.freeboard.adapter.out.persistence.entity.FreeBoardEntity;
import tdd.groomingzone.board.freeboard.adapter.out.persistence.repository.FreeBoardEntityRepository;
import tdd.groomingzone.board.freeboard.application.port.out.*;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

@Repository
public class FreeBoardPersistenceAdapter implements SaveFreeBoardPort, LoadFreeBoardPort {
    private final LoadMemberPort loadMemberPort;
    private final FreeBoardEntityRepository freeBoardEntityRepository;
    private final FreeBoardMapper freeBoardMapper;

    public FreeBoardPersistenceAdapter(LoadMemberPort loadMemberPort, FreeBoardEntityRepository freeBoardEntityRepository, FreeBoardMapper freeBoardMapper) {
        this.loadMemberPort = loadMemberPort;
        this.freeBoardEntityRepository = freeBoardEntityRepository;
        this.freeBoardMapper = freeBoardMapper;
    }

    @Override
    public FreeBoard save(long writerId, FreeBoard freeBoard) {
        FreeBoardEntity databaseEntity = freeBoardMapper.mapToDatabaseEntity(writerId, freeBoard);
        FreeBoardEntity savedFreeBoardEntity =  freeBoardEntityRepository.save(databaseEntity);
        Member writer = loadMemberPort.findMemberById(writerId);
        return freeBoardMapper.mapToDomainEntity(writer, savedFreeBoardEntity);
    }

    @Override
    public FreeBoard loadFreeBoardById(long freeBoardId) {
        FreeBoardEntity findFreeBoardEntity = findFreeBoardByIdOrElseThrowException(freeBoardId);
        Member writer = loadMemberPort.findMemberById(findFreeBoardEntity.getMemberId());
        return freeBoardMapper.mapToDomainEntity(writer, findFreeBoardEntity);
    }

    private FreeBoardEntity findFreeBoardByIdOrElseThrowException(long freeBoardId) {
        return freeBoardEntityRepository.findById(freeBoardId).orElseThrow(() ->
                new BusinessException(ExceptionCode.BOARD_NOT_FOUND));
    }
}
