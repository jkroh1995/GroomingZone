package tdd.groomingzone.board.recruitment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecruitment is a Querydsl query type for Recruitment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitment extends EntityPathBase<Recruitment> {

    private static final long serialVersionUID = 1224716154L;

    public static final QRecruitment recruitment = new QRecruitment("recruitment");

    public final tdd.groomingzone.board.QBoardEntity _super = new tdd.groomingzone.board.QBoardEntity(this);

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

    public final EnumPath<Recruitment.Type> type = createEnum("type", Recruitment.Type.class);

    //inherited
    public final NumberPath<Integer> viewCount = _super.viewCount;

    //inherited
    public final NumberPath<Long> writerId = _super.writerId;

    //inherited
    public final StringPath writerNickName = _super.writerNickName;

    public QRecruitment(String variable) {
        super(Recruitment.class, forVariable(variable));
    }

    public QRecruitment(Path<? extends Recruitment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecruitment(PathMetadata metadata) {
        super(Recruitment.class, metadata);
    }

}

