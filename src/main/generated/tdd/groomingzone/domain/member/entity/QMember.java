package tdd.groomingzone.domain.member.entity;

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

    private static final long serialVersionUID = -2114016749L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final tdd.groomingzone.global.QBaseEntity _super = new tdd.groomingzone.global.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final ListPath<tdd.groomingzone.domain.board.freeboard.entity.FreeBoard, tdd.groomingzone.domain.board.freeboard.entity.QFreeBoard> freeBoards = this.<tdd.groomingzone.domain.board.freeboard.entity.FreeBoard, tdd.groomingzone.domain.board.freeboard.entity.QFreeBoard>createList("freeBoards", tdd.groomingzone.domain.board.freeboard.entity.FreeBoard.class, tdd.groomingzone.domain.board.freeboard.entity.QFreeBoard.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<tdd.groomingzone.domain.board.review.Review, tdd.groomingzone.domain.board.review.QReview> reviews = this.<tdd.groomingzone.domain.board.review.Review, tdd.groomingzone.domain.board.review.QReview>createList("reviews", tdd.groomingzone.domain.board.review.Review.class, tdd.groomingzone.domain.board.review.QReview.class, PathInits.DIRECT2);

    public final ListPath<String, StringPath> roles = this.<String, StringPath>createList("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public final tdd.groomingzone.domain.barbershop.QBarberShop workPlace;

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.workPlace = inits.isInitialized("workPlace") ? new tdd.groomingzone.domain.barbershop.QBarberShop(forProperty("workPlace"), inits.get("workPlace")) : null;
    }

}

