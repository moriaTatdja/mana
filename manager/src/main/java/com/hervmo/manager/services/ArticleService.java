package com.hervmo.manager.services;

import com.hervmo.manager.models.entities.Articles;

public interface ArticleService {


    Articles editedArticle(Articles a, Long boxId);

    Articles returnArticle(Articles a, Long boxId);

}
