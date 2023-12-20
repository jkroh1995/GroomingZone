package tdd.groomingzone.global.pagedresponse;

import lombok.Getter;

import java.util.List;

@Getter
public final class PagedResponseDto<T> {
    private final List<T> data;
    private final PageInfo pageInfo;

    public PagedResponseDto(List<T> data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
