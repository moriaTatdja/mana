package com.hervmo.manager.restcontrollers.imp;


import com.hervmo.manager.models.entities.Articles;
import com.hervmo.manager.restcontrollers.ArticleRestController;
import com.hervmo.manager.services.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.hervmo.manager.utils.Constants.ARTICLE_ROUTE_NAME;

@CrossOrigin
@RestController
@Slf4j
public class ArticleRestControllerImp implements ArticleRestController {

    @Autowired
    protected ArticleService articleService;

    @Override
    public ResponseEntity getArticles(Long id, String hashCart) {
        Optional<List<Articles>> list = Optional.empty();
        if (hashCart == null) {
            hashCart = "check you code";
        }
        return ResponseEntity.ok().body(hashCart);
    }

    @Override
    public ResponseEntity<Articles> addArticle(Articles a, Long boxId) {
        articleService.editedArticle(a, boxId);
        return null;
    }
}
