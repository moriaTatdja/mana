package com.hervmo.manager.restcontrollers;


import com.hervmo.manager.models.entities.Articles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.hervmo.manager.utils.Constants.ARTICLE_ROUTE_NAME;

@CrossOrigin
@RequestMapping(ARTICLE_ROUTE_NAME)
public interface ArticleRestController {


    @GetMapping("/{id}")
    ResponseEntity getArticles(@PathVariable Long id, @RequestHeader("HASH_CART") String hashCart);

    @PostMapping("/{boxId}")
    ResponseEntity<Articles> addArticle(@RequestBody Articles a, @PathVariable Long boxId);
}
