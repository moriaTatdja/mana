package com.hervmo.manager.services.imp;

import com.hervmo.manager.models.entities.Articles;
import com.hervmo.manager.models.entities.Boxes;
import com.hervmo.manager.models.repositories.ArticlesRepository;
import com.hervmo.manager.models.repositories.BoxesRepository;
import com.hervmo.manager.services.ArticleService;
import com.hervmo.manager.services.HistoryService;
import com.hervmo.manager.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleServiceImp implements ArticleService {

    @Autowired
    protected ArticlesRepository articlesRepository;
    @Autowired
    protected HistoryService historyService;

    @Autowired
    protected WarehouseService warehouseService;

    @Autowired
    protected BoxesRepository boxRepo;

    @Override
    public Articles editedArticle(Articles a, Long boxId) {
        // @Todo ameliorate the logic , make it usable for remove 1 item , more or complete delete
        if (a.getArticleId() != null) {
            Optional<Articles> oldArticle = articlesRepository.findById(a.getArticleId());
            if (oldArticle.isPresent()) {
                a.setQty(oldArticle.get().getQty() - a.getQty());
                historyService.addArticleHistory(a, oldArticle.get());
            }
        }
        a = articlesRepository.saveAndFlush(a);
        Optional<Boxes> box = boxRepo.findById(boxId);
        if (box.isPresent()) {
            warehouseService.addArticleInbox(a, box.get(), a.getQty());
        }
        return a;
    }

    @Override
    public Articles returnArticle(Articles a, Long boxId) {
        Optional<Articles> old = articlesRepository.findById(a.getArticleId());
        if (a.getArticleId() != null && old.isPresent()) {
            a.setActive(false);
            a = articlesRepository.saveAndFlush(a);
            historyService.addArticleHistory(a, old.get());
            return a;
        }
        return null;
    }
}
