package tdd.groomingzone.post.freeboard.application.port.out;

import lombok.Data;

import org.springframework.data.domain.Page;
import tdd.groomingzone.post.freeboard.adapter.out.persistence.entity.FreeBoardEntity;
import tdd.groomingzone.post.freeboard.domain.FreeBoard;

import java.util.List;

@Data
public final class FreeBoardPage {
    private final List<FreeBoard> freeBoards;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;

    private FreeBoardPage(List<FreeBoard> freeBoards, Page<FreeBoardEntity> page) {
        this.freeBoards = freeBoards;
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }

    public static FreeBoardPage of(List<FreeBoard> freeBoards, Page<FreeBoardEntity> page) {
        return new FreeBoardPage(freeBoards, page);
    }
}
