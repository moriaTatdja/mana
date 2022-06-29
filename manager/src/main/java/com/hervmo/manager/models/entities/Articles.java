package com.hervmo.manager.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Articles extends InfoUtil {
    @SequenceGenerator(
            name = "article_sequence",
            sequenceName = "article_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "article_sequence"
    )
    @Id
    private Long articleId;
    private String articleNumber;
    @OneToMany(mappedBy = "articleId")
    private List<BoxArticlesMap> boxList = new ArrayList<>();
    private double price;
    private int qty;
    @Column(nullable = false)
    private String articleName;
    private String description;
    private Long ean;
    @ManyToOne(fetch = FetchType.EAGER)
    private States status;

}
