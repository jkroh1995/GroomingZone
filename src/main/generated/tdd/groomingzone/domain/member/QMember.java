package tdd.groomingzone.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1093418892L;

    public static final QMember member = new QMember("member1");

    public final tdd.groomingzone.global.QBaseEntity _super = new tdd.groomingzone.global.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<tdd.groomingzone.domain.board.freeboard.entity.FreeBoard, tdd.groomingzone.domain.board.freeboard.entity.QFreeBoard> freeBoards = this.<tdd.groomingzone.domain.board.freeboard.entity.FreeBoard, tdd.groomingzone.domain.board.freeboard.entity.QFreeBoard>createList("freeBoards", tdd.groomingzone.domain.board.freeboard.entity.FreeBoard.class, tdd.groomingzone.domain.board.freeboard.entity.QFreeBoard.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final ListPath<tdd.groomingzone.domain.board.recruitment.Recruitment, tdd.groomingzone.domain.board.recruitment.QRecruitment> recruitments = this.<tdd.groomingzone.domain.board.recruitment.Recruitment, tdd.groomingzone.domain.board.recruitment.QRecruitment>createList("recruitments", tdd.groomingzone.domain.board.recruitment.Recruitment.class, tdd.groomingzone.domain.board.recruitment.QRecruitment.class, PathInits.DIRECT2);

    public final ListPath<tdd.groomingzone.domain.board.review.Review, tdd.groomingzone.domain.board.review.QReview> reviews = this.<tdd.groomingzone.domain.board.review.Review, tdd.groomingzone.domain.board.review.QReview>createList("reviews", tdd.groomingzone.domain.board.review.Review.class, tdd.groomingzone.domain.board.review.QReview.class, PathInits.DIRECT2);

    public final EnumPath<Member.Role> role = createEnum("role", Member.Role.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

