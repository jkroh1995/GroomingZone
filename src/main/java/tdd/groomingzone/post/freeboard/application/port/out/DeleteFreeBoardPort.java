package tdd.groomingzone.post.freeboard.application.port.out;

import tdd.groomingzone.post.freeboard.application.port.out.query.DeleteFreeBoardQuery;

public interface DeleteFreeBoardPort {

    void delete(DeleteFreeBoardQuery query);
}
