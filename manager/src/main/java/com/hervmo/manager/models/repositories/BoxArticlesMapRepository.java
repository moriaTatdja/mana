package com.hervmo.manager.models.repositories;

import com.hervmo.manager.models.entities.Articles;
import com.hervmo.manager.models.entities.BoxArticlesMap;
import com.hervmo.manager.models.entities.Boxes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoxArticlesMapRepository extends JpaRepository<BoxArticlesMap, Long> {

    Optional<BoxArticlesMap> findByArticleIdAndBoxId(Articles a, Boxes b);
}
