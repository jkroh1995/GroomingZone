package tdd.groomingzone.board.freeboard;

public interface FreeBoardService {

    FreeBoardDto.Response postFreeBoard(FreeBoardDto.Post postDto);

    FreeBoardDto.Response putFreeBoard(long id, FreeBoardDto.Put putDto);

    FreeBoardDto.Response getFreeBoard(long id);
}
