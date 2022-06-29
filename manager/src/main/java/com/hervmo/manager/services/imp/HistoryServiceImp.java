package com.hervmo.manager.services.imp;

import com.hervmo.manager.models.entities.ArticleHistory;
import com.hervmo.manager.models.entities.Articles;
import com.hervmo.manager.models.repositories.HistoryRepository;
import com.hervmo.manager.services.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HistoryServiceImp implements HistoryService {

    @Autowired
    protected HistoryRepository historyRepo;


    @Override
    public void addArticleHistory(Articles newArticle, Articles oldArticle) {
        ArticleHistory articleHistory = getArticleHistory(newArticle, oldArticle);
        log.info("add ArticleHistory", historyRepo.saveAndFlush(articleHistory));
    }

    private ArticleHistory getArticleHistory(Articles newArticle, Articles oldArticle) {
        ArticleHistory articleHistory = new ArticleHistory();
        articleHistory.setArticle(newArticle);
        articleHistory.setNewArticleNumber(newArticle.getArticleNumber());
        articleHistory.setOldArticleNumber(oldArticle.getArticleNumber());
        articleHistory.setNewPrice(newArticle.getPrice());
        articleHistory.setOldPrice(oldArticle.getPrice());
        articleHistory.setNewQty(newArticle.getQty());
        articleHistory.setOldQty(oldArticle.getQty());
        articleHistory.setStatus(newArticle.getStatus().getStateName());
        articleHistory.setOldStatus(oldArticle.getStatus().getStateName());
        return articleHistory;
    }
}
