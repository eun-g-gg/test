package com.klpc.stadspring.domain.selectedContent.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSelectedContent is a Querydsl query type for SelectedContent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSelectedContent extends EntityPathBase<SelectedContent> {

    private static final long serialVersionUID = -1056783299L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSelectedContent selectedContent = new QSelectedContent("selectedContent");

    public final com.klpc.stadspring.domain.advert.entity.QAdvert advert;

    public final NumberPath<Long> fixedContentId = createNumber("fixedContentId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QSelectedContent(String variable) {
        this(SelectedContent.class, forVariable(variable), INITS);
    }

    public QSelectedContent(Path<? extends SelectedContent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSelectedContent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSelectedContent(PathMetadata metadata, PathInits inits) {
        this(SelectedContent.class, metadata, inits);
    }

    public QSelectedContent(Class<? extends SelectedContent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.advert = inits.isInitialized("advert") ? new com.klpc.stadspring.domain.advert.entity.QAdvert(forProperty("advert"), inits.get("advert")) : null;
    }

}

