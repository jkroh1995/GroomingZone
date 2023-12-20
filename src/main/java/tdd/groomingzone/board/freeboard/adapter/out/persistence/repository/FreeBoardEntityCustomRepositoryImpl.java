package tdd.groomingzone.board.freeboard.adapter.out.persistence.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import tdd.groomingzone.board.freeboard.adapter.out.persistence.entity.FreeBoardEntity;

import java.util.List;

import static tdd.groomingzone.board.freeboard.adapter.out.persistence.entity.QFreeBoardEntity.freeBoardEntity;

@Repository
public class FreeBoardEntityCustomRepositoryImpl implements FreeBoardEntityCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public FreeBoardEntityCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<FreeBoardEntity> findFilteredFreeBoards(String title, String content, String writer, Pageable pageable) {
        List<FreeBoardEntity> foundEntities = jpaQueryFactory.selectFrom(freeBoardEntity)
                .where(containTitle(title),
                        containContent(content),
                        containWriter(writer))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(freeBoardEntity.count())
                .from(freeBoardEntity)
                .fetchOne();

        return new PageImpl<>(foundEntities, pageable, count);
    }

    private BooleanExpression containTitle(String title) {
        if (!StringUtils.hasText(title)) {
            return null;
        }
        return freeBoardEntity.title.contains(title);
    }

    private BooleanExpression containContent(String content) {
        if (!StringUtils.hasText(content)) {
            return null;
        }
        return freeBoardEntity.content.contains(content);
    }

    private BooleanExpression containWriter(String writer) {
        if (!StringUtils.hasText(writer)) {
            return null;
        }
        return freeBoardEntity.writerNickName.contains(writer);
    }
}
