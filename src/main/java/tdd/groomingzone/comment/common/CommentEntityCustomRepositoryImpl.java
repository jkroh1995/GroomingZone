package tdd.groomingzone.comment.common;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static tdd.groomingzone.comment.common.QCommentEntity.commentEntity;
import static tdd.groomingzone.post.freeboard.adapter.out.persistence.entity.QFreeBoardEntity.freeBoardEntity;

@Repository
public class CommentEntityCustomRepositoryImpl implements CommentEntityCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public CommentEntityCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public Page<CommentEntity> findCommentPageByBoardId(long boardId, Pageable pageable) {
        List<CommentEntity> foundEntities = jpaQueryFactory.selectFrom(commentEntity)
                .where(commentEntity.boardId.eq(boardId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(freeBoardEntity.count())
                .from(freeBoardEntity)
                .fetchOne();

        return new PageImpl<>(foundEntities, pageable, count);
    }
}
