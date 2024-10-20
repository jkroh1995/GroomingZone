package tdd.groomingzone.comment.freeboardcomment.repository;

import org.springframework.data.domain.Pageable;
import tdd.groomingzone.post.common.PageNumber;

import static tdd.groomingzone.global.utils.CommonEnums.PAGE_SIZE;

public record FreeBoardCommentPageable(
        Long boardId,
        Pageable pageable
)  {
    public static FreeBoardCommentPageable of(Long boardId, PageNumber pageNumber){
        return new FreeBoardCommentPageable(boardId, Pageable.ofSize(PAGE_SIZE.getValue()).withPage(pageNumber.pageNumber()));
    }
}
