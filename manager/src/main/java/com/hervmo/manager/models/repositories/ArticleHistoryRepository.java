package com.hervmo.manager.models.repositories;

import com.hervmo.manager.models.entities.ArticleHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleHistoryRepository extends JpaRepository<ArticleHistory, Long> {
}
