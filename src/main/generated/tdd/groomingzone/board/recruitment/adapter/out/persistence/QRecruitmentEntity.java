package tdd.groomingzone.board.recruitment.adapter.out.persistence;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecruitmentEntity is a Querydsl query type for RecruitmentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitmentEntity extends EntityPathBase<RecruitmentEntity> {

    private static final long serialVersionUID = -708766993L;

    public static final QRecruitmentEntity recruitmentEntity = new QRecruitmentEntity("recruitmentEntity");

    public final tdd.groomingzone.board.common.QBoardEntity _super = new tdd.groomingzone.board.common.QBoardEntity(this);

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

    public final StringPath type = createString("type");

    //inherited
    public final NumberPath<Integer> viewCount = _super.viewCount;

    //inherited
    public final NumberPath<Long> writerId = _super.writerId;

    //inherited
    public final StringPath writerNickName = _super.writerNickName;

    public QRecruitmentEntity(String variable) {
        super(RecruitmentEntity.class, forVariable(variable));
    }

    public QRecruitmentEntity(Path<? extends RecruitmentEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecruitmentEntity(PathMetadata metadata) {
        super(RecruitmentEntity.class, metadata);
    }

}

