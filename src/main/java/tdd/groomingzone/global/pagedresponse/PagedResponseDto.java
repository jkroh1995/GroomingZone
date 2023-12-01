package tdd.groomingzone.global.pagedresponse;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PagedResponseDto<T> {
    private final List<T> data;
    private final PageInfo pageInfo;

    public PagedResponseDto(List<T> data, Page page) {
        this.data = data;
        this.pageInfo = new PageInfo(page.getNumber() + 1,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}
