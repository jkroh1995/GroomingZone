package tdd.groomingzone.post.review;

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

    private static final long serialVersionUID = -312917474L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final tdd.groomingzone.post.common.QBoardEntity _super = new tdd.groomingzone.post.common.QBoardEntity(this);

    public final tdd.groomingzone.barbershop.QBarberShop barberShop;

    //inherited
    public final StringPath content = _super.content;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath title = _super.title;

    //inherited
    public final NumberPath<Integer> viewCount = _super.viewCount;

    //inherited
    public final NumberPath<Long> writerId = _super.writerId;

    //inherited
    public final StringPath writerNickName = _super.writerNickName;

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
        this.barberShop = inits.isInitialized("barberShop") ? new tdd.groomingzone.barbershop.QBarberShop(forProperty("barberShop"), inits.get("barberShop")) : null;
    }

}

