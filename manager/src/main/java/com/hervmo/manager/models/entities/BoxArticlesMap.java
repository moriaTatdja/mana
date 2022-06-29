package com.hervmo.manager.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoxArticlesMap {
    @SequenceGenerator(
            name = "box_article_sequence",
            sequenceName = "box_article_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "box_article_sequence"
    )
    @Id
    private Long boxArticleId;
    @ManyToOne
    private Articles articleId;

    @ManyToOne
    private Boxes boxId;
    private int quantity;
}
