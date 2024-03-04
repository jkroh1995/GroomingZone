package tdd.groomingzone.post.freeboard.application.port.out;

import tdd.groomingzone.post.freeboard.application.service.PageNumber;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;

public interface LoadFreeBoardPort {

    FreeBoard loadFreeBoardById(long freeBoardId);

    FreeBoardPage loadFreeBoardPage(String title, String content, String writerNickName, PageNumber pageNumber);
}
