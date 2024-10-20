package tdd.groomingzone.post.freeboard.repository;

import tdd.groomingzone.post.common.PageNumber;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;

public interface LoadFreeBoardPort {

    FreeBoard loadFreeBoardById(long freeBoardId);

    FreeBoardPage loadFreeBoardPage(String title, String content, String writerNickName, PageNumber pageNumber);
}
