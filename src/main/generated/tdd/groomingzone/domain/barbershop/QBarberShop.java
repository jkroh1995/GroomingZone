package tdd.groomingzone.domain.barbershop;

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

    private static final long serialVersionUID = 1208275116L;

    public static final QBarberShop barberShop = new QBarberShop("barberShop");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<tdd.groomingzone.domain.board.review.Review, tdd.groomingzone.domain.board.review.QReview> reviews = this.<tdd.groomingzone.domain.board.review.Review, tdd.groomingzone.domain.board.review.QReview>createList("reviews", tdd.groomingzone.domain.board.review.Review.class, tdd.groomingzone.domain.board.review.QReview.class, PathInits.DIRECT2);

    public QBarberShop(String variable) {
        super(BarberShop.class, forVariable(variable));
    }

    public QBarberShop(Path<? extends BarberShop> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBarberShop(PathMetadata metadata) {
        super(BarberShop.class, metadata);
    }

}

