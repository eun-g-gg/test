package com.klpc.stadspring.domain.contents.categoryRelationship.entity;

import com.klpc.stadspring.domain.contents.category.entity.ContentCategory;
import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentCategoryRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_category_id")
    private ContentCategory contentCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_concept_id")
    private ContentConcept contentConcept;

    public static ContentCategoryRelationship createContentCategoryRelationship (
            ContentCategory contentCategory,
            ContentConcept contentConcept
    ) {
        ContentCategoryRelationship contentCategoryRelationship = new ContentCategoryRelationship();
        contentCategoryRelationship.contentCategory = contentCategory;
        contentCategoryRelationship.contentConcept = contentConcept;

        return contentCategoryRelationship;
    }
}
