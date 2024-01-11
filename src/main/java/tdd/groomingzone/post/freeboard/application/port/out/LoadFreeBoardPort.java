package tdd.groomingzone.post.freeboard.application.port.out;

public interface LoadFreeBoardPort {

    FreeBoardEntityQueryResult loadFreeBoardById(long freeBoardId);

    FreeBoardPageQueryResult loadFreeBoardPage(String title, String content, String writerNickName, FreeBoardPage freeBoardPage);
}
