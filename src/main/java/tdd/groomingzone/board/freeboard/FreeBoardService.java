package tdd.groomingzone.board.freeboard;

public interface FreeBoardService {

    FreeBoardDto.Response postFreeBoard(FreeBoardDto.Post postDto);

    FreeBoardDto.Response putFreeBoard(FreeBoardDto.Put putDto);
}
