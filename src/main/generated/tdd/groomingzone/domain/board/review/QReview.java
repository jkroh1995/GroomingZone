package tdd.groomingzone.domain.board.review;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = -887458236L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final tdd.groomingzone.domain.board.QBoard _super;

    public final tdd.groomingzone.domain.barbershop.QBarberShop barberShop;

    //inherited
    public final ListPath<tdd.groomingzone.domain.comment.Comment, tdd.groomingzone.domain.comment.QComment> comments;

    //inherited
    public final StringPath content;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt;

    //inherited
    public final StringPath title;

    // inherited
    public final tdd.groomingzone.domain.member.QMember writer;

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new tdd.groomingzone.domain.board.QBoard(type, metadata, inits);
        this.barberShop = inits.isInitialized("barberShop") ? new tdd.groomingzone.domain.barbershop.QBarberShop(forProperty("barberShop")) : null;
        this.comments = _super.comments;
        this.content = _super.content;
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.modifiedAt = _super.modifiedAt;
        this.title = _super.title;
        this.writer = _super.writer;
    }

}

