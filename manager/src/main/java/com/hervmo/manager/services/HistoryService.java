package com.hervmo.manager.services;

import com.hervmo.manager.models.entities.Articles;

public interface HistoryService {

    void addArticleHistory(Articles newArticle, Articles oldArticle);
}
