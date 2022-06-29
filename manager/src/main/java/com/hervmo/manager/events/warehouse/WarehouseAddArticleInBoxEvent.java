package com.hervmo.manager.events.warehouse;

import com.hervmo.manager.models.entities.Articles;
import com.hervmo.manager.models.entities.BoxArticlesMap;
import com.hervmo.manager.models.entities.Boxes;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class WarehouseAddArticleInBoxEvent extends ApplicationEvent {

    private Articles article;
    private Boxes box;
    private BoxArticlesMap boxArticlesMap;

    public WarehouseAddArticleInBoxEvent(Articles article, Boxes box, BoxArticlesMap boxArticlesMap) {
        super(article);
        this.article = article;
        this.box = box;
        this.boxArticlesMap = boxArticlesMap;
    }
}
