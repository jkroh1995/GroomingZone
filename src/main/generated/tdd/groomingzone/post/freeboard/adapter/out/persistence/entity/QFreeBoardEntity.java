package tdd.groomingzone.post.freeboard.adapter.out.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFreeBoardEntity is a Querydsl query type for FreeBoardEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFreeBoardEntity extends EntityPathBase<FreeBoardEntity> {

    private static final long serialVersionUID = 1096220068L;

    public static final QFreeBoardEntity freeBoardEntity = new QFreeBoardEntity("freeBoardEntity");

    public final tdd.groomingzone.post.common.QBoardEntity _super = new tdd.groomingzone.post.common.QBoardEntity(this);

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

    public QFreeBoardEntity(String variable) {
        super(FreeBoardEntity.class, forVariable(variable));
    }

    public QFreeBoardEntity(Path<? extends FreeBoardEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFreeBoardEntity(PathMetadata metadata) {
        super(FreeBoardEntity.class, metadata);
    }

}
