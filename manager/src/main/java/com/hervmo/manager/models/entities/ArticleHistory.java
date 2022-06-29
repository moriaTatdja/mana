package com.hervmo.manager.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleHistory extends History {
    private double oldPrice;
    private double newPrice;
    private String oldArticleNumber;
    private String newArticleNumber;
    private int oldQty;
    private int newQty;
    private String status;
    private String oldStatus;
    @ManyToOne
    private Articles article;

}
