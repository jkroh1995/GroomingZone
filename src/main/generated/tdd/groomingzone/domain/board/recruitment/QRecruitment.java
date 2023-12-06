package tdd.groomingzone.domain.board.recruitment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruitment is a Querydsl query type for Recruitment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitment extends EntityPathBase<Recruitment> {

    private static final long serialVersionUID = 935488544L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruitment recruitment = new QRecruitment("recruitment");

    public final tdd.groomingzone.domain.board.QBoard _super;

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

    public final EnumPath<Recruitment.Type> type = createEnum("type", Recruitment.Type.class);

    //inherited
    public final NumberPath<Integer> viewCount;

    // inherited
    public final tdd.groomingzone.domain.member.entity.QMember writer;

    public QRecruitment(String variable) {
        this(Recruitment.class, forVariable(variable), INITS);
    }

    public QRecruitment(Path<? extends Recruitment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruitment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruitment(PathMetadata metadata, PathInits inits) {
        this(Recruitment.class, metadata, inits);
    }

    public QRecruitment(Class<? extends Recruitment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new tdd.groomingzone.domain.board.QBoard(type, metadata, inits);
        this.comments = _super.comments;
        this.content = _super.content;
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.modifiedAt = _super.modifiedAt;
        this.title = _super.title;
        this.viewCount = _super.viewCount;
        this.writer = _super.writer;
    }

}

