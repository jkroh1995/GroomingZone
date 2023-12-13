package tdd.groomingzone.barbershop;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBarberShop is a Querydsl query type for BarberShop
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBarberShop extends EntityPathBase<BarberShop> {

    private static final long serialVersionUID = -708833786L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBarberShop barberShop = new QBarberShop("barberShop");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final tdd.groomingzone.member.entity.QMember owner;

    public final ListPath<tdd.groomingzone.board.review.Review, tdd.groomingzone.board.review.QReview> reviews = this.<tdd.groomingzone.board.review.Review, tdd.groomingzone.board.review.QReview>createList("reviews", tdd.groomingzone.board.review.Review.class, tdd.groomingzone.board.review.QReview.class, PathInits.DIRECT2);

    public QBarberShop(String variable) {
        this(BarberShop.class, forVariable(variable), INITS);
    }

    public QBarberShop(Path<? extends BarberShop> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBarberShop(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBarberShop(PathMetadata metadata, PathInits inits) {
        this(BarberShop.class, metadata, inits);
    }

    public QBarberShop(Class<? extends BarberShop> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.owner = inits.isInitialized("owner") ? new tdd.groomingzone.member.entity.QMember(forProperty("owner")) : null;
    }

}

