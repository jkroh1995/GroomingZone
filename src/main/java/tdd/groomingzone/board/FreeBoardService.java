package tdd.groomingzone.board;

public interface FreeBoardService {

    FreeBoardDto.Response postFreeBoard(FreeBoardDto.Post postDto);

    FreeBoardDto.Response putFreeBoard(FreeBoardDto.Put putDto);
}
