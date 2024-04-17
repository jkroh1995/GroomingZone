package tdd.groomingzone.post.freeboard.application.port.out;

import org.springframework.data.domain.Page;
import tdd.groomingzone.post.freeboard.adapter.out.persistence.entity.FreeBoardEntity;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;

import java.util.List;

public record FreeBoardPage(
        List<FreeBoard> freeBoards,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages

)  {
    public static FreeBoardPage of(List<FreeBoard> freeBoards, Page<FreeBoardEntity> page) {
        return new FreeBoardPage(freeBoards, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
