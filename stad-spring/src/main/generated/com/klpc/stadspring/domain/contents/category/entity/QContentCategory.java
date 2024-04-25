package com.klpc.stadspring.domain.contents.category.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContentCategory is a Querydsl query type for ContentCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContentCategory extends EntityPathBase<ContentCategory> {

    private static final long serialVersionUID = -1671301502L;

    public static final QContentCategory contentCategory = new QContentCategory("contentCategory");

    public final ListPath<com.klpc.stadspring.domain.contents.categoryRelationship.entity.ContentCategoryRelationship, com.klpc.stadspring.domain.contents.categoryRelationship.entity.QContentCategoryRelationship> contentCategoryRelationshipList = this.<com.klpc.stadspring.domain.contents.categoryRelationship.entity.ContentCategoryRelationship, com.klpc.stadspring.domain.contents.categoryRelationship.entity.QContentCategoryRelationship>createList("contentCategoryRelationshipList", com.klpc.stadspring.domain.contents.categoryRelationship.entity.ContentCategoryRelationship.class, com.klpc.stadspring.domain.contents.categoryRelationship.entity.QContentCategoryRelationship.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isMovie = createBoolean("isMovie");

    public final StringPath name = createString("name");

    public QContentCategory(String variable) {
        super(ContentCategory.class, forVariable(variable));
    }

    public QContentCategory(Path<? extends ContentCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QContentCategory(PathMetadata metadata) {
        super(ContentCategory.class, metadata);
    }

}

