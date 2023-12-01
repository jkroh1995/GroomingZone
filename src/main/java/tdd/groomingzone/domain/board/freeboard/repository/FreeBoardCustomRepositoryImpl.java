package tdd.groomingzone.domain.board.freeboard.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;

import java.util.List;

import static tdd.groomingzone.domain.board.freeboard.entity.QFreeBoard.freeBoard;

@Repository
public class FreeBoardCustomRepositoryImpl implements FreeBoardCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public FreeBoardCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<FreeBoard> findFilteredFreeBoards(String title, String content, String writer, Pageable pageable) {
        List<FreeBoard> foundEntities = jpaQueryFactory.selectFrom(freeBoard)
                .where(containTitle(title),
                        containContent(content),
                        containWriter(writer))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(freeBoard.count())
                .from(freeBoard)
                .fetchOne();

        return new PageImpl<>(foundEntities, pageable, count);
    }

    private BooleanExpression containTitle(String title) {
        if (!StringUtils.hasText(title)) {
            return null;
        }
        return freeBoard.title.contains(title);
    }

    private BooleanExpression containContent(String content) {
        if (!StringUtils.hasText(content)) {
            return null;
        }
        return freeBoard.content.contains(content);
    }

    private BooleanExpression containWriter(String writer) {
        if (!StringUtils.hasText(writer)) {
            return null;
        }
        return freeBoard.writer.name.contains(writer);
    }
}
